#! /usr/bin/env groovy

pipeline {

    agent {
        label 'maven'
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building ...'
                /*
                configFileProvider([configFile(fileId: '8dd52123-d487-4555-8a03-55888835d986', variable: 'MyGlobalSettings')]) {
                    sh 'mvn -s $MyGlobalSettings clean install'
                }
                */
                sh 'mvn clean install'
            }
        }
        stage('Create Container Image') {
            steps {
                echo 'Creating Container Image ...'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("cicd-demo") {
                            openshift.selector("bc", "spring-demo").startBuild() 
                        } 
                    }
                }
            }
        }
        stage('Cleaning Up') {  // It is supposed to replace it with gitops
            steps {
                echo 'Cleaning Up ...'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("cicd-demo") { 
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
                            openshift.newApp('spring-demo')  
                        } 
                    }
                }
            }
        }
    }
}