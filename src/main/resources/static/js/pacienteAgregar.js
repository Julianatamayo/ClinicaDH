document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("agregarForm");

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    const apellido = document.getElementById("apellido").value;
    const nombre = document.getElementById("nombre").value;
    const dni = document.getElementById("dni").value;
    const fecha = document.getElementById("fecha").value;
    const calle = document.getElementById("calle").value;
    const numero = document.getElementById("numero").value;
    const localidad = document.getElementById("localidad").value;
    const provincia = document.getElementById("provincia").value;

    const datosFormulario = {
      nombre,
      apellido,
      documento de identidad,
      fechaIngreso: fecha,
      domicilio: {
        calle,
        numero,
        localidad,
        provincia,
      },
    };

    fetch(`/paciente/guardar`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(datosFormulario),
    })
      .then((response) => {
        if (!response.ok) {
          // Si la respuesta no es OK, lanza un error con el código de estado
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();  // Asume que la respuesta es JSON
      })
      .then((data) => {
        console.log("Paciente agregado:", data);
        alert("Paciente agregado con éxito");
        form.reset();  // Resetea el formulario
      })
      .catch((error) => {
        console.error("Error agregando paciente:", error);
        alert(`Ocurrió un error al agregar el paciente: ${error.message}`);
      });
  });
});
