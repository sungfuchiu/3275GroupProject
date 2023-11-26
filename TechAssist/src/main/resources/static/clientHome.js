
window.addEventListener("load",function loadCustomerTop() {
    let categoryId = document.getElementById("lblSelectedCategory");
    let categoryPanel = document.getElementById("categoryPanel");
    let technicianPanel = document.getElementById("technicianPanel");
    let appointment = document.getElementById("lblAppointmentStart");
    let appointmentBar = document.getElementById("idAppointmentBar");
    let appointmentTime;

    // if(appointment.innerHTML == "") {
    //     appointmentBar.style.display = 'none';
    // } else {
    //     appointmentBar.style.display = 'flex;'
    // }

    if (categoryId.innerHTML != "") {
        categoryPanel.style.display = 'none';
        technicianPanel.style.display = 'flex';
    } else {
        categoryPanel.style.display = 'flex';
        technicianPanel.style.display = 'none';
    }

    if(appointment.innerHTML != "") {
        appointmentTime = new Date(appointment).getTime();
        appointmentTimeCountDown(appointmentTime);
    }

});

function appointmentTimeCountDown(appointmentTime) {
    let countdown = setInterval(function() {
        let viewCountdown = document.getElementById("lblAppointmentStart");
        let current = new Date().getTime();
        let distance;
        let minutes;
        let seconds;

        if(current < appointmentTime) {
            distance = appointmentTime - current;
            minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            seconds = Math.floor((distance % (1000 * 60)) / 1000);

            viewCountdown.innerHTML = "Appoinement Start " + minutes + " : " + seconds;

            if(distance < 0) {
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




