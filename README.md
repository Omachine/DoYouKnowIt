# Relatório do Projeto

## Estrutura do Projeto

O projeto segue a arquitetura MVVM (Model-View-ViewModel) e está organizado da seguinte forma:

- `com.example.doyouknowit`
  - `MainActivity.kt`
  - `AuthActivity.kt`
- `com.example.doyouknowit.ui`
  - `AppNavigation.kt`
  - `screens`
    - `CategorySelectorScreen.kt`
    - `QuizScreen.kt`
    - `LoginScreen.kt`
    - `RegistrationScreen.kt`
  - `theme`
    - `Color.kt`
    - `Theme.kt`
  - `viewmodel`
    - `UserViewModel.kt`
    - `QuizViewModel.kt`
    - `QuizViewModelFactory.kt`
- `com.example.doyouknowit.model`
  - `Question.kt`
  - `QuizState.kt`
- `com.example.doyouknowit.data.entities`
  - `User.kt`
  - `HighScore.kt`

## Lista de Funcionalidades da Aplicação

- Autenticação de usuários (login e registro)
- Seleção de categorias de quiz
- Realização de quizzes com temporizador
- Armazenamento e atualização de pontuações altas no Firebase Firestore

## Desenhos, Esquemas e Protótipos da Aplicação

*Esta seção deve incluir desenhos, esquemas e protótipos da aplicação. Adicione imagens ou links para os protótipos aqui.*

## Modelo de Dados

- **User**: Representa um usuário com `id`, `email` e `password`.
- **HighScore**: Representa uma pontuação alta com `userId`, `category` e `score`.
- **Question**: Representa uma questão de quiz com `text`, `choices` e `correctAnswer`.
- **QuizState**: Representa o estado do quiz com `isGameOver`, `score`, `currentQuestionIndex` e `questions`.

## Implementação do Projeto

A implementação do projeto foi realizada utilizando a linguagem Kotlin e a framework Jetpack Compose para a interface do usuário. A autenticação e o armazenamento de dados foram feitos utilizando o Firebase Authentication e o Firebase Firestore, respectivamente.

## Tecnologias Usadas

- **Linguagens**: Kotlin
- **Frameworks**: Jetpack Compose
- **Serviços de Backend**: Firebase Authentication, Firebase Firestore

## Dificuldades

Na pontuação mostrada na seleção de categorias consegui fazer com que jogar e alcançar pontuação 0 não alterar o Highscore. No entanto qualquer número acima de 0 alterava apesar de ser abaixo do anterior.

## Conclusões

O projeto foi implementado quase completo como planeado mas em geral acredito que consegui desenvolver as minhas habilidades com sucesso.
