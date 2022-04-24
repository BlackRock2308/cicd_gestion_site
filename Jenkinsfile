pipeline {

    agent any
 
    tools {
        maven "Maven"
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "localhost:8081"
        NEXUS_REPOSITORY = "nexus-snapshot"
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
        stage("Maven Build") {
            steps {
                script {
                    bat "mvn clean install -DskipTests=true"
                }
            }
        }

    stage('SonarQube analysis') {
        steps {
            script {
                def scannerHome = tool 'My SonarQube Server';
                 withSonarQubeEnv('My SonarQube Server') {
                 bat "${scannerHome}/bin/sonar-scanner \
                 -D sonar.login=admin \
                 -D sonar.password=Lifeisagift30 \
                 -D sonar.projectKey=sn.ept.git.seminaire.cicd \
                 -D sonar.exclusions=vendor/**,resources/**,**/*.java \
                 -D sonar.host.url=http://localhost:9000/"
                }
            }
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
         stage("Deploy to apache Server") {
        steps{
            script{
                deploy adapters: [tomcat9(credentialsId: 'tomcat_credentials', path: '', url: 'http://localhost:8082')], contextPath: '/target', onFailure: false, war: '**/*.war'
            }
        }
    }
    }
}
