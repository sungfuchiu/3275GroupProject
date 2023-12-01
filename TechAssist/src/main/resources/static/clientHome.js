
window.addEventListener("load",function loadCustomerTop() {
    let categoryId = document.getElementById("lblSelectedCategory");
    let categoryPanel = document.getElementById("categoryPanel");
    let technicianPanel = document.getElementById("technicianPanel");
    let appointment = document.getElementById("hidAppointmentStart");
    let appointmentText = document.getElementById("lblAppointmentStart");
    let btnStartCall = document.getElementById("idBtnStartCall");
    let appointmentTime;

    if (categoryId.innerHTML != "") {
        categoryPanel.style.display = 'none';
        technicianPanel.style.display = 'flex';
    } else {
        categoryPanel.style.display = 'flex';
        technicianPanel.style.display = 'none';
    }

    if(appointment.value != "") {
        btnStartCall.disabled = false;
        appointmentTime = new Date(appointment.value).getTime();
        appointmentTimeCountDown(appointmentTime);
    } else {
        appointmentText.innerHTML = "No Appointment";
        btnStartCall.disabled = true;
    }

});

function appointmentTimeCountDown(appointmentTime) {
    let countdown = setInterval(function() {
        let viewCountdown = document.getElementById("lblAppointmentStart");
        let current = new Date().getTime();
        let distance;
        let days;
        let hours
        let minutes;
        let seconds;

        if(current < appointmentTime) {
            distance = appointmentTime -current;
            days = Math.floor(distance / (1000 * 60 * 60 * 24));
            hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            seconds = Math.floor((distance % (1000 * 60)) / 1000);
            viewCountdown.innerHTML = "Appt: " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";

            if(distance <= 0) {
                clearInterval(countdown);
                viewCountDown.innerHTML = "Please start video calling";
            }
        }
    }, 1000);
}

function showCategoryPanel() {
    let categoryName = document.getElementById("lblSelectedCategory");
    let categoryPanel = document.getElementById("categoryPanel");

    if(categoryPanel.style.display == "none") {
        categoryPanel.style.display = "flex";
    } else if(categoryName.innerHTML != "" && categoryPanel.style.display == "flex") {
        categoryPanel.style.display = "none";
    }
}




