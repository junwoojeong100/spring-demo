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
      }
    }
    stage('Create Container Image') {
      steps {
        echo 'Create Container Image..'
        
        script {

            // Add steps here
            openshift.withCluster() { 
                openshift.withProject("spring-demo") {
                    def buildConfigExists = openshift.selector("bc", "spring-demo").exists() 
                    
                    if(!buildConfigExists){ 
                    openshift.newBuild("--name=spring-demo", "--image=quay.io/jjeong/spring-demo:latest", "--binary") 
                    } 
                    
                    openshift.selector("bc", "spring-demo").startBuild("--from-file=target/*.jar", "--follow") 
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

/*
pipeline {

    agent {
        label 'maven'
    }

    stages {
        stage('maven install') {
            steps {
                sh 'mvn clean install'
            }
        }
  
        container('buildah') {
            stage('buildah build & push') {
                steps {
                    sh 'buildah build -t jjeong/spring-demo:latest .'
                    withCredentials([usernamePassword(credentialsId: 'quay.io', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                        sh '''
                            buildah login -u ${env.dockerHubUser} -p ${env.dockerHubPassword} quay.io
                            buildah push https://quay.io/jjeong/spring-demo:latest
                        '''
                    }
                }
            }    
        }

    } 
}
*/