<!DOCTYPE html>
<html>

<head>

  <style>

    body {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

  </style>

</head>

<body>

  <img src="${url.resourcesUrl}/img/logo.png" width="200" />
  <p>Prezado(a) responsável,<p>
 
  <p>
    Este e-mail confirma a autorização para que <strong>${fullName}</strong> utilize o aplicativo [Nome do App].
    Com a sua autorização, todas as funcionalidades do smartwatch e do aplicativo estarão disponíveis para uso,
    sem interrupções. O tratamento dos dados será realizado de forma segura e em conformidade com a
    <strong>Lei Geral de Proteção de Dados (LGPD - Lei nº 13.709/2018)</strong>.
  </p>
  
  <p>
    Reforçamos nosso compromisso com a privacidade e a proteção das informações do menor,
    garantindo transparência e segurança em todo o processo.
  </p>
  
  <p>
    Caso tenha alguma dúvida ou encontre alguma dificuldade, entre em contato conosco através
    do e-mail ${technosEmail} ou pelo whatsapp: ${technosPhone}.
  </p>
  
  <p>
    <a href="${confirmationLink}">Clique aqui para confirmar!</a>
    <#-- ATENÇÃO: Esse é um e-mail automático de confirmação e não deve ser respondido. -->
  </p>

</body>

</html>