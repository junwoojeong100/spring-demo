#! /usr/bin/env groovy

pipeline {

    agent {
        label 'maven'
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                
                // Add steps here
                sh 'mvn clean install'
                sh 'pwd'
                sh 'ls /tmp/workspace/spring-demo/target/'
                
            }
        }
        stage('Create Container Image') {
            steps {
                echo 'Create Container Image..'
                
                script {

                    // Add steps here
                    openshift.withCluster() { 
                        openshift.withProject("spring-demo") {
                            def buildConfigExists = openshift.selector("bc", "s2i-build").exists() 

                            if(!buildConfigExists){ 
                                //openshift.newBuild("--name=spring-demo", "--to=quay.io/jjeong/spring-demo:latest", "--binary=true") 
                            } 
                            
                            openshift.selector("bc", "spring-demo").startBuild("--from-file=target/demo-1.0.0.jar", "--follow") 
                        } 
                    }

                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                script {

                    // Add steps here
                    openshift.withCluster() { 
                        openshift.withProject("spring-demo") { 
                            def deployment = openshift.selector("dc", "spring-demo") 
                            
                            if(!deployment.exists()){ 
                                openshift.newApp('spring-demo', "--as-deployment-config").narrow('svc').expose() 
                            } 
                            
                            timeout(5) { 
                                openshift.selector("dc", "spring-demo").related('pods').untilEach(1) { 
                                    return (it.object().status.phase == "Running") 
                                } 
                            } 
                        } 
                    }
                }
            }
        }
    }
}