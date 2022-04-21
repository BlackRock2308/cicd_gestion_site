node {
    try {
        stage('Checkout') {
            echo "Init"
        }
        stage ('Package Stage') {
            bat './mvnw package'
        }
        stage('Test & Jacoco Static Analysis') {
            junit 'target/surefire-reports/**/*.xml'
            jacoco()
        }
        stage('Sonar Scanner Coverage') {
            bat "./mvnw sonar:sonar -Dsonar.login=06ccbabcaff40aa03ab2c41d4baa9a1b0c999293 -Dsonar.host.url=http://localhost:9000"
        }
        stage ('Publish to Nexus') {
            nexusPublisher nexusInstanceId: 'Nexus-ID', nexusRepositoryId: 'gestion_site_cicd_nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: './target/gestion-site-cicd']],mavenCoordinate: [artifactId: 'cicd', groupId: 'sn.ept.git.seminaire', packaging: 'jar', version: '0.0.1-SNAPSHOT']]]
        }
        stage ('Deploy on this Server') {
            echo "Deployement"
            //deploy adapters: [tomcat9(credentialsId: 'TOMCAT_CREDENTIAL_IN_SETTINGS', path: '', url: 'http://localhost:8085')], contextPath: null, war: '**/*.jar'
        }
    }
    catch (e) {
        echo e
    }
}
