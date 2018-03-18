pipeline {
  agent any
  stages {
    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Sonar') {
      steps {
        script {
          withSonarQubeEnv('Sonar') {
            sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar ' +
            '-Dsonar.java.libraries=. ' +
            '-Dsonar.branch = ${BRANCH_NAME}'
          }
        }
        
      }
    }
    stage('Check Quality Gate') {
      steps {
        echo 'Checking quality gate...'
        script {
          timeout(time: 1, unit: 'HOURS') {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
          }
        }
        
      }
    }
  }
  tools {
    maven 'Maven'
  }
}