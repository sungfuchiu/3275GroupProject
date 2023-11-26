window.addEventListener("load",function loadRegister() {
    let userType = document.getElementById("hidUserType");
    let btnClient = document.getElementById("radClient");
    let btnTechnician = document.getElementById("radTechnician");
    let targetRow1 = document.getElementById("trForTechnicianDes");
    let targetRow2 = document.getElementById("trForTechnicianService");
    let jobDescribtion = document.getElementById("idJobDescription");
    let selectedServiceField = document.getElementById("hidServiceField");
    let serviceField = document.getElementById("selServiceField");

    if(userType.value == 'technician') {
        btnTechnician.checked = true;
        targetRow1.style.display = 'table-row';
        targetRow2.style.display = 'table-row';
        jobDescribtion.required = true;
        serviceField.required = true;
        serviceField.value = selectedServiceField.value;
    } else {
        btnClient.checked = true;
        targetRow1.style.display = 'none';
        targetRow2.style.display = 'none';
        jobDescribtion.required = false;
        serviceField.required = false;
    }

    // if(btnClient.checked == true) {
    //     targetRow1.style.display = 'none';
    //     targetRow2.style.display = 'none';
    //     jobDescribtion.required = false;
    //     serviceField.required = false;
    // } else if(btnTechnician.checked == true) {
    //     targetRow1.style.display = 'table-row';
    //     targetRow2.style.display = 'table-row';
    //     jobDescribtion.required = true;
    //     serviceField.required = true;
    // }
});

function changeInputInfo() {
    let targetRow1 = document.getElementById("trForTechnicianDes");
    let targetRow2 = document.getElementById("trForTechnicianService");
    let btnClient = document.getElementById("radClient");
    let btnTechnician = document.getElementById("radTechnician");
    let jobDescribtion = document.getElementById("idJobDescription");
    let serviceField = document.getElementById("selServiceField");

    if(btnClient.checked == true) {
        targetRow1.style.display = 'none';
        targetRow2.style.display = 'none';
        jobDescribtion.required = false;
        serviceField.required = false;
    } else if(btnTechnician.checked == true) {
        targetRow1.style.display = 'table-row';
        targetRow2.style.display = 'table-row';
        jobDescribtion.required = true;
        serviceField.required = true;
    }
}