pipeline {
parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

    agent any

     environment {
           NEXUS_VERSION = "nexus3"
           NEXUS_PROTOCOL = "http"
           NEXUS_URL = "localhost:8081"
           NEXUS_REPOSITORY = "maven-snapshots"
           NEXUS_CREDENTIAL_ID = "admin"
       }

    options {
            timeout(time: 15, unit: 'MINUTES')
        }


    tools {
          maven "Maven"
          jdk "jdk2_8"   
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
                    git 'https://github.com/BlackRock2308/cicd_gestion_site.git'
                }
            }

         }
         stage('SonarQube analysis') {
         steps {
            script {
                withSonarQubeEnv(credentialsId: 'ConnectJenkins', installationName: 'My SonarQube Server') { // You can override the credential to be used
                 bat 'mvn clean deploy -DskipTests=true sonar:sonar'
                }
            }
         }

         }

stage("Quality Gate"){
        steps{
                script{
                         timeout(time: 1, unit: 'HOURS') {
                              def qg = waitForQualityGate()
                              if (qg.status != 'OK') {
                                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
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
