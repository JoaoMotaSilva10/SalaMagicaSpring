pipeline {
    agent any

    environment {
        // Definir variáveis de ambiente do SonarQube
        SONARQUBE_SERVER = 'SonarQube' // Nome da instância do SonarQube configurada no Jenkins
        GITHUB_REPO = 'https://github.com/programadormovel/miniprojetospring2024.git'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clonar o repositório do GitHub
                git branch: 'main', url: env.GITHUB_REPO
            }
        }

        stage('Build') {

            steps {
                withMaven(globalMavenSettingsConfig: '', jdk: '11', maven: '3.2.5', mavenSettingsConfig: '', publisherStrategy: 'EXPLICIT', traceability: true) {
                    // some block
                    // Compilar o projeto (substitua 'mvn clean install' pelo comando adequado para seu projeto)
                    sh 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                // Variáveis SonarQube
                SONAR_SCANNER_HOME = tool 'SonarQube Scanner' // Nome do scanner configurado no Jenkins
            }
            steps {
                // Executar a análise do SonarQube
                withSonarQubeEnv(env.SONARQUBE_SERVER) {
                    sh "${env.SONAR_SCANNER_HOME}/bin/sonar-scanner -Dsonar.projectKey=miniprojetospring2024 -Dsonar.sources=src -Dsonar.java.binaries=target"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Aguardar e verificar o status da análise do SonarQube
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy') {
            steps {
                // Executar o deploy do projeto (modifique conforme necessário para seu ambiente)
                echo 'Deploy do projeto iniciado...'
                // Exemplo: script de deploy ou comando específico
                // sh 'bash deploy_script.sh'
            }
        }
    }

    post {
        always {
            // Enviar e-mails ou notificação de falha
            echo 'Pipeline finalizado!'
        }
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Falha no pipeline!'
        }
    }
}
