#! /usr/bin/env groovy

pipeline {

    agent {
        label 'maven'
    }

    stages {
        stage('Build') {

                echo 'Building..'
                
                // Add steps here
                sh 'mvn clean install'

                //sh 'pwd'
                //sh 'ls /tmp/workspace/spring-demo/target/'
                

        }
        stage('Create Container Image') {

                echo 'Create Container Image..'
                
                script {

                    // Add steps here
                    openshift.withCluster() { 
                        openshift.withProject("spring-demo") {
                            def buildConfigExists = openshift.selector("bc", "s2i-build").exists() 

                            if(!buildConfigExists){ 
                                //openshift.newBuild("--name=spring-demo", "--to=quay.io/jjeong/spring-demo:latest", "--binary=true") 
                            } 
                            
                            //openshift.selector("bc", "s2i-build").startBuild("--from-file=target/demo-1.0.0.jar", "--follow") 
                            openshift.selector("bc", "s2i-build").startBuild() 
                        } 
                    }

                }

        }
        stage('Cleaningup') {

                echo 'Cleaning up....'
                script {
                    openshift.withCluster() { 
                        openshift.withProject("spring-demo") { 
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
        stage('Deploy') {

                echo 'Deploying....'
                script {

                    // Add steps here
                    openshift.withCluster() { 
                        openshift.withProject("spring-demo") { 
                            //def deployment = openshift.selector("dc", "spring-demo") 

                            //if(!deployment.exists()){ 
                                //openshift.newApp('spring-demo', "--as-deployment-config").narrow('svc').expose() 
                            //} 

                            openshift.newApp('spring-demo')
                            
                            /*
                            timeout(5) { 
                                openshift.selector("dc", "spring-demo").related('pods').untilEach(1) { 
                                openshift.selector("deploy", "spring-demo").related('pods').untilEach(1) { 
                                    return (it.object().status.phase == "Running") 
                                } 
                            }*/
                        } 
                    }
                }

        }
    }
}