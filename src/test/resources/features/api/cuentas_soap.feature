# language: es
@api @soap
Característica: Servicio SOAP de banca
  Como sistema integrador del banco
  Quiero consumir el servicio SOAP de ParaBank
  Para autenticar clientes, consultar cuentas y registrar transferencias

  @smoke
  Escenario: Autenticación de un cliente
    Cuando el cliente demo se autentica por SOAP
    Entonces el código de respuesta debería ser 200
    Y la respuesta SOAP debería contener "firstName" igual a "John"

  Escenario: Consulta del detalle de una cuenta
    Dado que el cliente demo se autentica por SOAP
    Cuando consulta por SOAP el detalle de su cuenta principal
    Entonces el código de respuesta debería ser 200
    Y la cuenta devuelta debería pertenecer al cliente autenticado

  @smoke
  Escenario: Apertura de cuenta y transferencia por SOAP
    Dado que el cliente demo se autentica por SOAP
    Y abre por SOAP una cuenta de tipo "SAVINGS"
    Cuando transfiere por SOAP 10 desde su cuenta principal hacia la nueva cuenta
    Entonces el código de respuesta debería ser 200
    Y la respuesta SOAP debería contener "transferReturn" que incluya "Successfully transferred $10"

  Escenario: Consulta de una cuenta inexistente por SOAP
    Cuando consulta por SOAP la cuenta 99999999
    Entonces el código de respuesta debería ser 500
    Y la respuesta SOAP debería contener "faultstring" que incluya "Could not find account #99999999"
