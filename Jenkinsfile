
pipeline {
    agent any

    options {
            timeout(time: 15, unit: 'MINUTES')
        }

    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

    tools {
          maven "Maven_3.3.9"
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
