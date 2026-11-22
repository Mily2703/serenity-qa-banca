# language: es
@web
Característica: Banca por internet - cuentas y transferencias
  Como cliente del banco
  Quiero gestionar mis cuentas desde la banca por internet
  Para mover mi dinero sin tener que ir a una agencia

  Antecedentes:
    Dado que Milagros se registra como nueva cliente en la banca por internet

  @smoke
  Escenario: Registro exitoso de una nueva cliente
    Entonces debería ver la bienvenida con su nombre de usuario
    Y el mensaje "Your account was created successfully. You are now logged in."

  Escenario: Apertura de una cuenta de ahorros
    Cuando abre una nueva cuenta de tipo "SAVINGS"
    Entonces debería ver el título "Account Opened!"
    Y el número de su nueva cuenta

  @smoke
  Escenario: Transferencia entre cuentas propias
    Cuando abre una nueva cuenta de tipo "SAVINGS"
    Y transfiere 50 desde su cuenta principal hacia la nueva cuenta
    Entonces debería ver el título "Transfer Complete!"
    Y el monto transferido debería ser "$50.00"

  # BUG-001: la página no muestra la validación y envía la transferencia vacía al servidor (ver README)
  @defecto-conocido
  Escenario: Transferencia sin ingresar monto
    Cuando intenta transferir sin ingresar un monto
    Entonces debería ver el error de monto "The amount cannot be empty."
