# language: es
@api @rest
Característica: Servicio REST de cuentas y transferencias
  Como canal digital del banco
  Quiero consumir el servicio REST de cuentas
  Para consultar saldos y ejecutar transferencias de los clientes

  Antecedentes:
    Dado que el cliente demo se autentica en el servicio REST

  @smoke
  Escenario: Consulta de las cuentas del cliente
    Cuando consulta sus cuentas
    Entonces el código de respuesta debería ser 200
    Y debería tener al menos una cuenta de tipo "CHECKING"
    Y todas sus cuentas deberían pertenecer al cliente autenticado

  @smoke
  Escenario: Apertura de cuenta y transferencia entre cuentas propias
    Dado que abre una cuenta de tipo "SAVINGS" con el depósito mínimo de 100
    Cuando transfiere 25 desde su cuenta principal hacia la nueva cuenta por REST
    Entonces el código de respuesta debería ser 200
    Y el mensaje debería contener "Successfully transferred $25"
    Y el saldo de la nueva cuenta debería ser 125

  Esquema del escenario: Consulta de una cuenta inexistente
    Cuando consulta la cuenta <cuenta>
    Entonces el código de respuesta debería ser 400
    Y el mensaje debería contener "Could not find account #<cuenta>"

    Ejemplos:
      | cuenta   |
      | 99999999 |
      | 88888888 |
