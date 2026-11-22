# language: es
@web
Característica: Inicio de sesión en la banca por internet
  Como banco
  Quiero validar los datos de acceso
  Para que solo los clientes autenticados ingresen a sus cuentas

  Escenario: Inicio de sesión sin credenciales
    Dado que Milagros abre la banca por internet
    Cuando inicia sesión sin ingresar usuario ni contraseña
    Entonces debería ver el error de acceso "Please enter a username and password."
