pipeline {
parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

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


         stage('SCM') {
            steps {
                script {
                     checkout scm
                }
            }

         }
         stage('SonarQube analysis') {

         steps {
            script {
                def mvn = tool 'Default Maven';
                withSonarQubeEnv() {
                bat "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=gestion-site-cicd-sonar"
                  }
                }
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