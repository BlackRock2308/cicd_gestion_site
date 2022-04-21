
pipeline {
    agent any

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
