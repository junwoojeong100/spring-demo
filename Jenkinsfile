#! /usr/bin/env groovy

pipeline {

    agent {
        label 'maven'
    }

    stages {
        stage('Maven Build') {
            steps {
                echo 'Building ...'
                /*
                configFileProvider([configFile(fileId: 'af0e1436-6947-46ec-8d14-82ab913367de', variable: 'MyGlobalSettings')]) {
                    withSonarQubeEnv('SonarQubeServer') {
                        sh 'mvn -s $MyGlobalSettings clean package sonar:sonar -DskipTests'
                    }
                }
                */
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn -s config/settings.xml clean package sonar:sonar -DskipTests'
                }
            }
        }
        stage('Camel Maven Plugin') {
            steps {
                echo 'Processing Camel Maven Plugin ...'
            }
        }
        stage('Create Container Image') { 
            steps {
                echo 'Creating Container Image ...'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("cicd-jenkins") {
                            openshift.selector("bc", "spring-demo").startBuild("--from-file=target/spring-demo-1.0.0.jar") 
                        } 
                    }
                }
            }
        }
        stage('Prisma Cloud Image Scanning') {
            steps {
                echo 'Processing Prisma Cloud Image Scanning ...'
            }
        }
        stage('Cleaning Up') {  // It is supposed to replace it with gitops
            steps {
                echo 'Cleaning Up ...'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("cicd-jenkins") { 
                            def route = openshift.selector("route", "spring-demo") 
                            if(route.exists()){
                                route.delete()
                            }
                            def service = openshift.selector("service", "spring-demo") 
                            if(service.exists()){
                                service.delete()
                            }
                            def deployment = openshift.selector("deploy", "spring-demo") 
                            if(deployment.exists()){
                                deployment.delete()
                            }
                        }
                    }
                }
            }
        }
        stage('Deploy') {   // It is supposed to replace it with gitops
            steps {
                echo 'Deploying ...'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("cicd-jenkins") { 
                            openshift.newApp("quay.io/jjeong/spring-demo:latest", "--name=spring-demo").narrow('svc').expose("--path=/hello")
                        } 
                    }
                }
            }
        }
    }
}