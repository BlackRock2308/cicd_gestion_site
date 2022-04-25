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

        stage('Check Scm Changelog') {
                  steps {
                      script {
                        def changeLogSets = currentBuild.changeSets
                        for (int i = 0; i < changeLogSets.size(); i++) {
                            def entries = changeLogSets[i].items
                            for (int j = 0; j < entries.length; j++) {
                                def entry = entries[j]
                                if(entry.author.toString().contains('Jenkins') || entry.msg.contains('maven-release-plugin')){
                                    echo "Les Commit effectués par le user jenkins sont toujours ignorés. C'est le cas des releases effectuées depuis la chaine d'integration avec le user jenkins"
                                    currentBuild.result = 'ABORTED'
                                    error('Aucun besoin de builder de façon cyclique  les commits de Jenkins')
                                    return
                                } else {
                                    echo "ID Commit : ${entry.commitId} \nAuteur : ${entry.author} \nDate : ${new Date(entry.timestamp)} \nMessage: ${entry.msg}"
                                    def files = new ArrayList(entry.affectedFiles)
                                    for (int k = 0; k < files.size(); k++) {
                                        def file = files[k]
                                        echo "  ${file.editType.name} ${file.path}"
                                    }

                                }
                            }
                        }
                      }
                  }
                }
        }



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
                     bat 'mvn clean test -Dmaven.test.failure.ignore=true'
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
                   junit 'cicd_gestion_site/target/surefire-reports/**/*.xml'
               }
            }
        }


       stage("Jacoco Code Coverage") {
            steps {
                script {
                    echo "Code coverage"
                    jacoco()
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

        stage("Quality Gate"){

              steps {
                    script {
                        timeout(time: 1, unit: 'HOURS') {
                            waitForQualityGate abortPipeline: true
                        }

                    }
              }
        }

       stage('Deploy DEV') {
                when {
                   branch 'master'
                }
                steps {
                    script {
                        echo 'Should deploy on DEV env'
                    }
                }
       }

       stage('Check Deploy DEV ') {
              when {
                  branch 'master'
              }
              steps {
                script{
                    //sleep time: 30, unit: 'SECONDS'
                    //def url = 'http://178.170.114.95:8090/users-management/'
                     echo 'Should deploy on DEV env'
                    }
               }
       }

       stage('Deploy REC') {
            when {
               branch 'rec'
            }
            steps {
                script {
                    echo 'Should deploy on REC env'
                }
            }
       }

       stage('Check Deploy rec ') {
           when {
               branch 'rec'
            }
           steps {
             script{
                    echo "Should Deploy on REC env"
                 //sleep time: 30, unit: 'SECONDS'
                 //def url = 'http://178.170.114.95:8090/users-management/'
             }
          }
       }





       stage("Maven Build") {
            steps {
                script {
                    bat "mvn install -DskipTests=true"
                }
            }
       }


       stage("Release on Nexus") {
           when {
              branch 'release'
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
                script {
                    echo "Send Mail"
                    bat "mvn clean"
                }

      }
      post {
            changed {
                 emailext (
                       subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                       body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                       <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                       recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                   )
                 }
                    failure {
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



