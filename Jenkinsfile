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
                configFileProvider([configFile(fileId: '8dd52123-d487-4555-8a03-55888835d986', variable: 'MyGlobalSettings')]) {
                    sh 'mvn -s $MyGlobalSettings clean install'
                }
                */
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Maven Sonar Plugin') {
            steps {
                echo 'Processing Maven Sonar Plugin ...'
            }
        }
        stage('Camel Maven Plugin') {
            steps {
                echo 'Processing Camel Maven Plugin ...'
            }
        }
        stage('Create Container Image') { // It is supposed to replace it with gitops
            steps {
                echo 'Creating Container Image ...'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("cicd-demo") {
                            openshift.selector("bc", "spring-demo").startBuild("--from-file=target/demo-1.0.0.jar") 
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
                        openshift.withProject("cicd-demo") { 
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
                        openshift.withProject("cicd-demo") { 
                            openshift.newApp("quay.io/jjeong/spring-demo:latest", "--name=spring-demo").narrow('svc').expose("--path=/hello")
                        } 
                    }
                }
            }
        }
    }
}