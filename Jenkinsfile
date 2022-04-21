
pipeline {
    agent any

     environment {
           NEXUS_VERSION = "nexus3"
           NEXUS_PROTOCOL = "http"
           NEXUS_URL = "192.168.56.1:8081"
           NEXUS_REPOSITORY = "gestion_site_cicd_nexus"
           NEXUS_CREDENTIAL_ID = "nexus-user-credentials"

           //SONARQUBE_URL = "http://192.168.56.1"
           //SONARQUBE_PORT = "9000"
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

        node {
          stage('SCM withy SonarQube') {
            checkout scm
          }
          stage('SonarQube Analysis') {
            when {
                branch 'master'
            }
            def mvn = tool 'Maven';
            withSonarQubeEnv() {
                bat "mvn sonar:sonar -Dsonar.login= 06ccbabcaff40aa03ab2c41d4baa9a1b0c999293 -Dsonar.host.url=http://localhost:9000"            }
          }
        }


        stage("deploy") {
            steps {
                script {
                    echo 'Deployment of the app'
                }
            }
        }

        // Release on Nexus
/*
         stage('Deploy Artifact To Nexus') {
           when {
            branch 'master'
           }
           steps {
            script {
             unstash 'pom'
             unstash 'artifact'
             // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
             pom = readMavenPom file: "pom.xml";
             // Find built artifact under target folder
             filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
             // Print some info from the artifact found
             echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
             // Extract the path from the File found
             artifactPath = filesByGlob[0].path;
             // Assign to a boolean response verifying If the artifact name exists
             artifactExists = fileExists artifactPath;
             if (artifactExists) {
              nexusArtifactUploader(
               nexusVersion: NEXUS_VERSION,
               protocol: NEXUS_PROTOCOL,
               nexusUrl: NEXUS_URL,
               groupId: pom.groupId,
               version: pom.version,
               repository: NEXUS_REPOSITORY,
               credentialsId: NEXUS_CREDENTIAL_ID,
               artifacts: [
                // Artifact generated such as .jar, .ear and .war files.
                [artifactId: pom.artifactId,
                 classifier: '',
                 file: artifactPath,
                 type: pom.packaging
                ],
                // Lets upload the pom.xml file for additional information for Transitive dependencies
                [artifactId: pom.artifactId,
                 classifier: '',
                 file: "pom.xml",
                 type: "pom"
                ]
               ]
              )
             } else {
              error "*** File: ${artifactPath}, could not be found";
             }
            }
           }
          }
*/



       //End release on Nexus

      }
   }

}
