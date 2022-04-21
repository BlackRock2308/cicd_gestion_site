
def pingServerAfterDeployment( url){
  def response = httpRequest url

  if (response.status < 200 || response.status > 399){
     error("Le déploiement ne s'est pas bien passé : code de retour sur URL ${url} : ${response.status}")
     return
  }else{
      echo "Test de déploiement en environnement ${url} réussi ...."
  }
}

pipeline {
  agent any

    options {
        timeout(time: 15, unit: 'MINUTES')
    }

    tools {
      maven "Maven_3.3.9"
    }

    environment {
            NEXUS_VERSION = "nexus3"
            NEXUS_PROTOCOL = "http"
            NEXUS_URL = "192.168.56.1:8081"
            NEXUS_REPOSITORY = "gestion_site_cicd_nexus"
            NEXUS_CREDENTIAL_ID = "nexus-credentials"
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


      stage('Tests') {
          steps {
            sh 'mvn clean test'
          }

          post {
            success {
                junit 'tracking/target/surefire-reports/**/*.xml'
            }
          }
      }


/**
    stage('Quality gate') {
      steps{
        script{
            withSonarQubeEnv('SonarQubeServer') {
             sh 'mvn sonar:sonar'
            echo 'Should deploy on REC env'
           }
        }
      }
    }**/

    stage('Deploy DEV') {
       when {
          branch 'master'
       }
       steps {
         echo 'Should deploy on DEV env'
       }
    }


    stage('Check Deploy DEV ') {
      when {
          branch 'master'
      }
      steps {
        script{
            sleep time: 30, unit: 'SECONDS'
            def url = 'http://178.170.114.95:8090/users-management/'
             /*pingServerAfterDeployment (url)*/
             echo 'Should deploy on DEV env'
            }
       }
    }


    stage('Deploy REC') {
       when {
          branch 'rec'
       }
       steps {
         echo 'Should deploy on REC env'
       }
    }

    stage('Check Deploy rec ') {
      when {
          branch 'develop'
       }
      steps {
        script{
            sleep time: 30, unit: 'SECONDS'
            def url = 'http://178.170.114.95:8090/users-management/'
            /*pingServerAfterDeployment (url)*/
           }
       }
    }

    stage('Release On Nexus') {
       when {
          branch 'release'
       }
       steps {
          echo 'Release On Nexus'
       }
    }


  stage("Publish to Nexus Repository Manager") {

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



  }

  post {

    always{
        sh 'mvn clean'
    }

  }
}
