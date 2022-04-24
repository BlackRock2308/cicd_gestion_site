EmailReceivers = 'smbaye@ept.sn'

pipeline {

    agent any

    tools {
        maven "Maven"
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.56.1:8081"
        NEXUS_REPOSITORY = "gestion-site-snapshot"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
        SONAR_CREDENTIAL_ID = ""
    }

    stages {

        stage("Clone code from VCS") {
            steps {
                script {
                    git 'https://github.com/BlackRock2308/cicd_gestion_site.git';
                }
            }
        }

        stage('Tests') {
            steps {
                script {
                    //echo "Skip my test"
                     bat 'mvn clean'
                }
           }

            post {
               failure {
                // send to email
                 emailext (
                      subject: "POST TEST: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                       body: """<p>TEST STATUS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                       <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                       )
                   //junit 'tracking/target/surefire-reports/**/*.xml'
               }
            }
        }


    stage("SonarQube Analysis") {
        steps {
            script {
                def scannerHome = tool 'My SonarQube Server';
                 withSonarQubeEnv('My SonarQube Server') {
                 bat "${scannerHome}/bin/sonar-scanner \
                 -D sonar.login=admin \
                 -D sonar.password=Lifeisagift30 \
                 -D sonar.projectKey=gestion-site-cicd-sonar \
                 -D sonar.exclusions=vendor/**,resources/**,**/*.java \
                 -D sonar.host.url=http://192.168.56.1:9000/"
                }
            }
        }

      }


/**
      stage("Quality Gate"){

          steps {
                script {

                    timeout(time: 1, unit: 'HOURS') {

                        waitForQualityGate abortPipeline: true
                   }
                }
          }

       } **/









       stage("Maven Build") {
           steps {
               script {
                   bat "mvn install -DskipTests=true"
               }
           }
       }





        stage("Publish to Nexus Repository Manager") {
            when {
                branch 'feat-nexus-config'
            }
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }

        stage("Email Notification") {
             steps {
                  echo "Send Mail"
             }
             post {
                   success {
                          echo "This block runs when the stage succeeded."
                   }

                   always {
                      // send to email
                      emailext (
                          subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                          body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                            <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )

                   }
             }
        }




    }








}

