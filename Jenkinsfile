pipeline {


    agent any

     environment {
           NEXUS_VERSION = "nexus3"
           NEXUS_PROTOCOL = "http"
           NEXUS_URL = "192.168.56.1:8081"
           NEXUS_REPOSITORY = "gestion-site-maven-group"
           NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
       }

    options {
            timeout(time: 15, unit: 'MINUTES')
        }


    tools {
          maven "Maven"
        }

    stages {

      stage('Check Scm Changelog') {
          steps {
              script {
                echo "Checking Scm Changelog"
               }
             }
         }

        stage("init") {
            steps {
                script {
                  echo 'Initialisation of the app'
                }
            }
        }

        stage("test") {
            steps {
                 script {
                      echo 'Testing of the app'
                 }
            }
        }

        stage("build") {
            steps {
                script {
                     bat 'mvn clean package'
                }
            }
        }

        stage("Upload to Nexus") {
            steps {
                nexusArtifactUploader artifacts [
                    [
                        artifactId: 'cicd',
                        classifier: '',
                        file: 'target/tracking-1.0.0.war',
                        type: 'war',
                    ]
                ],
                credentialsId: 'nexus3',
                groupeId: 'sn.ept.git.seminaire',
                nexusUrl: 'localhost',
                nexusVersion: 'nexus3',
                protocol: 'http',
                repository: 'gestion-site-release',
                version: '1.0.0',
            }
        }

        stage("deploy") {
            steps {
                script {
                    echo 'Deployment of the app'
                }
            }
        }
    }

}