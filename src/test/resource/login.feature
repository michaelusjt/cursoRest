Feature: BarrigaRest

  @AcessarConta
  Scenario: Acessar a conta sem token
    Given que o usuário tenha acesso a api seu barriga
    When o usuário efetuar a requisição na api "seu barriga"
    Then o sitema retorna o status "401"