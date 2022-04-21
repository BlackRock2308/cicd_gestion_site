
pipeline {
    agent any

     environment {
           NEXUS_VERSION = "nexus3"
           NEXUS_PROTOCOL = "http"
           NEXUS_URL = "192.168.56.1:8081/repository/gestion_site_cicd_nexus/"
           NEXUS_REPOSITORY = "gestion_site_cicd_nexus"
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
        stage("build") {
            steps {
                script {
                     echo 'Building of the app'
                }
            }
        }


        stage("test") {
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                script {
                     echo 'Testing of the app'
                }
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
