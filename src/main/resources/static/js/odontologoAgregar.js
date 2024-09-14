const form = document.getElementById("agregarForm");

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const apellido = document.getElementById("apellido").value;
  const nombre = document.getElementById("nombre").value;
  const matricula = document.getElementById("matricula").value;
  const fecha = document.getElementById("fecha").value;

  // Preparar los datos para el formulario
  const datosFormulario = {
    nombre,
    apellido,
    matricula,
    fechaIngreso: fecha
  };

  fetch(`odontologo/guardar`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(datosFormulario),
  })
    .then(response => {
      if (response.ok) {
        return response.json(); // Intentar parsear la respuesta como JSON
      } else {
        return response.text().then(text => {
          // Manejar respuestas que no sean JSON
          throw new Error(`Error ${response.status}: ${text}`);
        });
      }
    })
    .then(data => {
      console.log(data);
      alert("Odontologo agregado con éxito");
      form.reset(); // Resetear el formulario
    })
    .catch(error => {
      console.error("Error agregando odontologo:", error);
      alert("Ocurrió un error al agregar el odontologo: " + error.message);
    });
});
