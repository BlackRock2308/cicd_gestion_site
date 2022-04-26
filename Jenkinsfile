EmailReceivers = 'smbaye@ept.sn'

def tomcatWeb = 'C:\\apache-tomcat-9.0.62\\webapps'
def tomcatBin = 'C:\\apache-tomcat-9.0.62\\bin'
def tomcatStatus = ''


def pingServerAfterDeployment( url){
  def response = httpRequest url

  if (response.status < 200 || response.status > 399){
     error("Le déploiement ne s'est pas bien passé : code de retour sur URL ${url} : ${response.status}")
     return
  }else{
      echo "Test de déploiement en environnement ${url} réussi, Kudos ...."
  }
}


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
                     bat 'mvn clean test -Dmaven.test.failure.ignore=true'
                }
           }

            post {
               failure {
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
                        bat "copy target\\tracking.war \"${tomcatWeb}\\tracking.war\""

                
                    }
                }
       }

       stage('Check Deploy DEV ,starting tomcat server') {
              when {
                  branch 'master'
              }
              steps {
                    script{
                         sleep(time:5, unit: "SECONDS")
                         bat "${tomcatBin}\\startup.bat"
                         sleep(time:100, unit: "SECONDS")
                         def url = 'http://localhost:8085/'
                         pingServerAfterDeployment (url)
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
                    bat "copy target\\tracking.war \"${tomcatWeb}\\tracking.war\""

                }
            }
       }

       stage('Check Deploy rec , start tomcat server') {
           when {
               branch 'rec'
            }
           steps {
                 script{
                     sleep(time:5, unit: "SECONDS")
                     bat "${tomcatBin}\\startup.bat"
                     sleep(time:100, unit: "SECONDS")
                     //sleep time: 30, unit: 'SECONDS'
                     def url = 'http://localhost:8085/'
                     pingServerAfterDeployment (url)
                     echo 'Should deploy on DEV env'

                 }
           }


           post {
                failure {
                     emailext (
                        subject: "ERRORS DEPLOYMENT REC: REC '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                        body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                        <p>Your artifcat is not deployed on tomcat server</p>
                        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                    )
                }

                success {
                    emailext (
                        subject: "DEPLOYMENT OF REC IN TOMCAT '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                        body: """<p>REC BRANCH: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                        <p>Your artificat in rec is deployed successfully</p>
                        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                    )
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
          post {
                success {
                    emailext (
                        subject: "NOTIFICATION AFTER PUBLISHING IN NEXUS: RELEASE '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                        body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                        <p>Your snapshot is published on Nexus successfully</p>
                        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                    )
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
                  success {
                      emailext (
                            subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                            body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                            <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                            )
                  }
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
}

