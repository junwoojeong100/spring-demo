pipeline {

    agent {
        label 'maven'
    }

  stages {
    stage('Maven Install') {
      steps {
        sh 'mvn clean install'
      }
    }
    /*
    stage('Docker Build') {
      steps {
        sh 'docker build -t shanem/spring-petclinic:latest .'
      }
    }
    stage('Docker Push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
          sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
          sh 'docker push shanem/spring-petclinic:latest'
        }
      }
    }    
    */
  }
}
/*
pipeline {

    agent {
        label 'maven'
    }

    stage('build') {
        withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh 'git clone https://$USERNAME:$PASSWORD@github.com/junw9293/spring-demo.git'
        }

        container('maven') {
            stage('mvn package') {
                dir('jenkins-on-eks/spring-boot') {
                    sh 'mvn -Dmaven.repo.local=/var/jenkins_home/.m2/repository clean package'
                }
            }
        }

        container('docker') {
            stage('docker build & push') {
                dir('jenkins-on-eks/spring-boot') {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh '''
                        docker build -t hello-spring-boot .
                        docker tag hello-spring-boot openzon/hello-spring-boot:${BUILD_NUMBER}
                        docker login -u=$USERNAME -p=$PASSWORD
                        docker push openzon/hello-spring-boot:${BUILD_NUMBER}
                    '''
                    }
                }
            }
        }
    }

    stage('deploy') {
        container('kubectl') {
            stage('kubectl set image') {
                sh '''
                    kubectl -n default set image deployment/hello-spring-boot hello-spring-boot=openzon/hello-spring-boot:${BUILD_NUMBER} --record
                '''
            }
        }
    }
    
}
*/