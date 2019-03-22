#!groovy​

pipeline {
    agent {
        node 'maven'
    }

    environment {
        SERVICE_VERSION = VersionNumber(projectStartDate: '2019-01-01', worstResultForIncrement: 'SUCCESS'
            , versionNumberString: '${BUILD_DATE_FORMATTED,"yyyyMMdd"}-r${BUILDS_TODAY, XX}', versionPrefix: '')
        SERVICE_NAME = 'simple-app'
        SCM_URL = 'ssh://git@github.com:benjamin-lang/simple-app.git'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }

    stages {
        stage('init') {
            steps {
                echo sh(returnStdout: true, script: 'env')
            }
        }
        stage('display version') {
            when {
                branch 'master'
            }
            steps {
                script {
                    currentBuild.displayName = SERVICE_VERSION
                }
            }
        }
        stage('ssh checkout') {
            steps {
                git(url: SCM_URL, credentialsId: 'jenkins2_ssh_credentials', branch: '$BRANCH_NAME')
            }
        }
        stage('build') {
            steps {
                withMaven(globalMavenSettingsConfig: "d4262a90-c29b-4b7e-9d17-cba6b9a45109") {
                    echo sh(returnStdout: true, script:"mvn help:effective-settings | grep localRepository")
                    sh "mvn versions:set -DnewVersion=$SERVICE_VERSION"
                    sh 'mvn verify'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}