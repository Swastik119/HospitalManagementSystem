// Hospital Management System - Web Version
// Tab Navigation System - ADD THIS AT THE TOP
function showTab(tabName) {
    // Hide all tab contents
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(tab => {
        tab.classList.remove('active');
    });
    
    // Remove active class from all nav buttons
    const navButtons = document.querySelectorAll('.nav-btn');
    navButtons.forEach(button => {
        button.classList.remove('active');
    });
    
    // Show selected tab
    const selectedTab = document.getElementById(tabName);
    if (selectedTab) {
        selectedTab.classList.add('active');
    }
    
    // Add active class to clicked nav button
    const clickedButton = document.querySelector(`[data-tab="${tabName}"]`);
    if (clickedButton) {
        clickedButton.classList.add('active');
    }
    
    // Update form options when switching to relevant tabs
    if (['appointments', 'medical', 'billing'].includes(tabName)) {
        hospitalSystem.updateSelectOptions();
    }
}

// Initialize tab system when page loads
document.addEventListener('DOMContentLoaded', function() {
    // Add click event listeners to all navigation buttons
    const navButtons = document.querySelectorAll('.nav-btn');
    navButtons.forEach(button => {
        button.addEventListener('click', function() {
            const tabName = this.getAttribute('data-tab');
            showTab(tabName);
        });
    });
    
    // Show dashboard by default
    showTab('dashboard');
    
    // Set minimum date for appointment to today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('appointmentDate').min = today;
    
    hospitalSystem.showNotification('Hospital Management System loaded successfully!', 'success');
});


    // ... ALL YOUR EXISTING CODE REMAINS THE SAME ...
class HospitalManagementSystem {
    constructor() {
        this.patients = [];
        this.doctors = [];
        this.appointments = [];
        this.medicalRecords = [];
        this.bills = [];
        this.departments = [
            { id: 1, name: "Cardiology", description: "Heart and cardiovascular diseases", floor: 2 },
            { id: 2, name: "Neurology", description: "Brain and nervous system disorders", floor: 3 },
            { id: 3, name: "Orthopedics", description: "Bone and joint diseases", floor: 1 },
            { id: 4, name: "Pediatrics", description: "Child healthcare", floor: 4 },
            { id: 5, name: "Emergency", description: "Emergency medical services", floor: 1 }
        ];
        
        this.patientIdCounter = 1;
        this.doctorIdCounter = 1;
        this.appointmentIdCounter = 1;
        this.medicalRecordIdCounter = 1;
        this.billIdCounter = 1;
        
        this.initializeSampleData();
        this.updateDashboard();
        this.loadAllData();
    }

    initializeSampleData() {
        // Sample Doctors
        this.doctors = [
            {
                id: this.doctorIdCounter++,
                name: "Dr. Smith",
                contactNumber: "123-456-7890",
                email: "smith@hospital.com",
                specialization: "Cardiologist",
                department: this.departments[0],
                experienceYears: 15,
                qualification: "MD",
                consultationFee: 150,
                availableSlots: ["09:00-10:00", "10:00-11:00", "11:00-12:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"]
            },
            {
                id: this.doctorIdCounter++,
                name: "Dr. Johnson",
                contactNumber: "123-456-7891",
                email: "johnson@hospital.com",
                specialization: "Neurologist",
                department: this.departments[1],
                experienceYears: 10,
                qualification: "MD",
                consultationFee: 200,
                availableSlots: ["09:00-10:00", "10:00-11:00", "11:00-12:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"]
            }
        ];

        // Sample Patients
        this.patients = [
            {
                id: this.patientIdCounter++,
                name: "John Doe",
                contactNumber: "123-555-0001",
                email: "john@email.com",
                age: 35,
                gender: "Male",
                address: "123 Main St",
                emergencyContact: "123-555-0002",
                bloodGroup: "O+",
                allergies: ["Penicillin"],
                registrationDate: new Date().toISOString().split('T')[0],
                totalBillAmount: 0
            },
            {
                id: this.patientIdCounter++,
                name: "Jane Smith",
                contactNumber: "123-555-0003",
                email: "jane@email.com",
                age: 28,
                gender: "Female",
                address: "456 Oak Ave",
                emergencyContact: "123-555-0004",
                bloodGroup: "A+",
                allergies: ["Shellfish"],
                registrationDate: new Date().toISOString().split('T')[0],
                totalBillAmount: 0
            }
        ];
    }

    // Patient Management
    addPatient(patientData) {
        const patient = {
            id: this.patientIdCounter++,
            ...patientData,
            registrationDate: new Date().toISOString().split('T')[0],
            totalBillAmount: 0,
            allergies: patientData.allergies ? patientData.allergies.split(',').map(a => a.trim()) : []
        };
        this.patients.push(patient);
        this.showNotification('Patient added successfully!', 'success');
        this.updateDashboard();
        this.loadPatients();
        return patient;
    }

    // Doctor Management
    addDoctor(doctorData) {
        const doctor = {
            id: this.doctorIdCounter++,
            ...doctorData,
            availableSlots: ["09:00-10:00", "10:00-11:00", "11:00-12:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"],
            department: this.departments.find(dept => dept.name === doctorData.department)
        };
        this.doctors.push(doctor);
        this.showNotification('Doctor added successfully!', 'success');
        this.updateDashboard();
        this.loadDoctors();
        return doctor;
    }

    // Appointment Management
    scheduleAppointment(appointmentData) {
        const patient = this.patients.find(p => p.id == appointmentData.patientId);
        const doctor = this.doctors.find(d => d.id == appointmentData.doctorId);
        
        if (!patient || !doctor) {
            this.showNotification('Patient or Doctor not found!', 'error');
            return null;
        }

        // Check if time slot is available
        if (!doctor.availableSlots.includes(appointmentData.timeSlot)) {
            this.showNotification('Time slot not available!', 'error');
            return null;
        }

        const appointment = {
            id: this.appointmentIdCounter++,
            patientId: appointmentData.patientId,
            doctorId: appointmentData.doctorId,
            patient: patient,
            doctor: doctor,
            appointmentDateTime: appointmentData.date + 'T10:00:00',
            timeSlot: appointmentData.timeSlot,
            status: 'SCHEDULED',
            description: appointmentData.description,
            fee: doctor.consultationFee
        };

        // Remove time slot from doctor's availability
        const doctorIndex = this.doctors.findIndex(d => d.id == appointmentData.doctorId);
        this.doctors[doctorIndex].availableSlots = this.doctors[doctorIndex].availableSlots.filter(slot => slot !== appointmentData.timeSlot);

        this.appointments.push(appointment);
        this.showNotification('Appointment scheduled successfully!', 'success');
        this.updateDashboard();
        this.loadAppointments();
        
        // Simulate notification
        setTimeout(() => {
            this.showNotification(`Appointment reminder: ${patient.name} with Dr. ${doctor.name} at ${appointmentData.timeSlot}`, 'warning');
        }, 2000);

        return appointment;
    }

    completeAppointment(appointmentId) {
        const appointment = this.appointments.find(a => a.id == appointmentId);
        if (appointment) {
            appointment.status = 'COMPLETED';
            
            // Add fee to patient's bill
            const patient = this.patients.find(p => p.id == appointment.patientId);
            if (patient) {
                patient.totalBillAmount += appointment.fee;
            }
            
            this.showNotification('Appointment completed!', 'success');
            this.loadAppointments();
            this.updateDashboard();
        }
    }

    cancelAppointment(appointmentId) {
        const appointment = this.appointments.find(a => a.id == appointmentId);
        if (appointment) {
            appointment.status = 'CANCELLED';
            
            // Return time slot to doctor's availability
            const doctor = this.doctors.find(d => d.id == appointment.doctorId);
            if (doctor) {
                doctor.availableSlots.push(appointment.timeSlot);
                doctor.availableSlots.sort();
            }
            
            this.showNotification('Appointment cancelled!', 'warning');
            this.loadAppointments();
            this.updateDashboard();
        }
    }

    // Medical Records
    addMedicalRecord(recordData) {
        const patient = this.patients.find(p => p.id == recordData.patientId);
        const doctor = this.doctors.find(d => d.id == recordData.doctorId);
        
        if (!patient || !doctor) {
            this.showNotification('Patient or Doctor not found!', 'error');
            return null;
        }

        const medicalRecord = {
            id: this.medicalRecordIdCounter++,
            patientId: recordData.patientId,
            doctorId: recordData.doctorId,
            patient: patient,
            doctor: doctor,
            diagnosis: recordData.diagnosis,
            treatment: recordData.treatment,
            treatmentCost: parseFloat(recordData.treatmentCost),
            prescription: recordData.prescription,
            notes: recordData.notes,
            treatmentDate: new Date().toISOString().split('T')[0]
        };

        // Add treatment cost to patient's bill
        patient.totalBillAmount += medicalRecord.treatmentCost;

        this.medicalRecords.push(medicalRecord);
        this.showNotification('Medical record added successfully!', 'success');
        this.loadMedicalRecords();
        this.updateDashboard();
        return medicalRecord;
    }

    // Billing
    generateBill(patientId) {
        const patient = this.patients.find(p => p.id == patientId);
        if (!patient) {
            this.showNotification('Patient not found!', 'error');
            return null;
        }

        const bill = {
            id: this.billIdCounter++,
            patientId: patientId,
            patient: patient,
            billDate: new Date().toISOString(),
            items: [],
            totalAmount: patient.totalBillAmount,
            status: 'GENERATED'
        };

        // Add completed appointments to bill
        this.appointments
            .filter(app => app.patientId == patientId && app.status === 'COMPLETED')
            .forEach(app => {
                bill.items.push({
                    description: `Consultation with Dr. ${app.doctor.name}`,
                    amount: app.fee
                });
            });

        // Add medical records to bill
        this.medicalRecords
            .filter(record => record.patientId == patientId)
            .forEach(record => {
                bill.items.push({
                    description: `Treatment: ${record.treatment}`,
                    amount: record.treatmentCost
                });
            });

        this.bills.push(bill);
        this.showNotification('Bill generated successfully!', 'success');
        this.loadBills();
        return bill;
    }

    payBill(billId, amount) {
        const bill = this.bills.find(b => b.id == billId);
        if (bill) {
            if (amount >= bill.totalAmount) {
                bill.status = 'PAID';
                const patient = this.patients.find(p => p.id == bill.patientId);
                if (patient) {
                    patient.totalBillAmount = 0;
                }
                this.showNotification('Bill fully paid! Thank you!', 'success');
            } else if (amount > 0) {
                bill.status = 'PARTIALLY_PAID';
                const patient = this.patients.find(p => p.id == bill.patientId);
                if (patient) {
                    patient.totalBillAmount -= amount;
                }
                this.showNotification(`Partial payment of $${amount} received!`, 'warning');
            }
            this.loadBills();
            this.updateDashboard();
        }
    }

    // Data Loading for UI
    loadPatients() {
        const tbody = document.getElementById('patientsList');
        tbody.innerHTML = this.patients.map(patient => `
            <tr>
                <td>${patient.id}</td>
                <td>${patient.name}</td>
                <td>${patient.age}</td>
                <td>${patient.gender}</td>
                <td>${patient.contactNumber}</td>
                <td>${patient.bloodGroup}</td>
                <td>
                    <button class="btn btn-secondary" onclick="viewPatientDetails(${patient.id})">
                        <i class="fas fa-eye"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    }

    loadDoctors() {
        const tbody = document.getElementById('doctorsList');
        tbody.innerHTML = this.doctors.map(doctor => `
            <tr>
                <td>${doctor.id}</td>
                <td>${doctor.name}</td>
                <td>${doctor.specialization}</td>
                <td>${doctor.department.name}</td>
                <td>${doctor.experienceYears} years</td>
                <td>$${doctor.consultationFee}</td>
                <td>${doctor.contactNumber}</td>
            </tr>
        `).join('');
    }

    loadAppointments() {
        const tbody = document.getElementById('appointmentsList');
        tbody.innerHTML = this.appointments.map(appointment => `
            <tr>
                <td>${appointment.id}</td>
                <td>${appointment.patient.name}</td>
                <td>Dr. ${appointment.doctor.name}</td>
                <td>${appointment.appointmentDateTime.split('T')[0]}</td>
                <td>${appointment.timeSlot}</td>
                <td>
                    <span class="status-${appointment.status.toLowerCase()}">${appointment.status}</span>
                </td>
                <td>$${appointment.fee}</td>
                <td>
                    ${appointment.status === 'SCHEDULED' ? `
                        <button class="btn btn-success" onclick="hospitalSystem.completeAppointment(${appointment.id})">
                            <i class="fas fa-check"></i>
                        </button>
                        <button class="btn btn-danger" onclick="hospitalSystem.cancelAppointment(${appointment.id})">
                            <i class="fas fa-times"></i>
                        </button>
                    ` : ''}
                </td>
            </tr>
        `).join('');
    }

    loadMedicalRecords() {
        const tbody = document.getElementById('medicalList');
        tbody.innerHTML = this.medicalRecords.map(record => `
            <tr>
                <td>${record.id}</td>
                <td>${record.patient.name}</td>
                <td>Dr. ${record.doctor.name}</td>
                <td>${record.diagnosis}</td>
                <td>${record.treatment}</td>
                <td>$${record.treatmentCost}</td>
                <td>${record.treatmentDate}</td>
            </tr>
        `).join('');
    }

    loadBills() {
        const tbody = document.getElementById('billsList');
        tbody.innerHTML = this.bills.map(bill => `
            <tr>
                <td>${bill.id}</td>
                <td>${bill.patient.name}</td>
                <td>$${bill.totalAmount}</td>
                <td>
                    <span class="status-${bill.status.toLowerCase()}">${bill.status}</span>
                </td>
                <td>${bill.billDate.split('T')[0]}</td>
                <td>
                    <button class="btn btn-primary" onclick="viewBillDetails(${bill.id})">
                        <i class="fas fa-eye"></i> View
                    </button>
                    ${bill.status !== 'PAID' ? `
                        <button class="btn btn-success" onclick="showPaymentForm(${bill.id}, ${bill.totalAmount})">
                            <i class="fas fa-credit-card"></i> Pay
                        </button>
                    ` : ''}
                </td>
            </tr>
        `).join('');
    }

    updateDashboard() {
        document.getElementById('totalPatients').textContent = this.patients.length;
        document.getElementById('totalDoctors').textContent = this.doctors.length;
        
        const today = new Date().toISOString().split('T')[0];
        const todayApps = this.appointments.filter(app => 
            app.appointmentDateTime.split('T')[0] === today && app.status === 'SCHEDULED'
        ).length;
        document.getElementById('todayAppointments').textContent = todayApps;
        
        const totalRevenue = this.bills
            .filter(bill => bill.status === 'PAID')
            .reduce((sum, bill) => sum + bill.totalAmount, 0);
        document.getElementById('totalRevenue').textContent = `$${totalRevenue}`;
    }

    loadAllData() {
        this.loadPatients();
        this.loadDoctors();
        this.loadAppointments();
        this.loadMedicalRecords();
        this.loadBills();
        this.updateSelectOptions();
    }

    updateSelectOptions() {
        // Update patient selects
        const patientSelects = ['appointmentPatient', 'medicalPatient', 'billPatient'];
        patientSelects.forEach(selectId => {
            const select = document.getElementById(selectId);
            if (select) {
                select.innerHTML = '<option value="">Select Patient</option>' +
                    this.patients.map(patient => 
                        `<option value="${patient.id}">${patient.name} (ID: ${patient.id})</option>`
                    ).join('');
            }
        });

        // Update doctor selects
        const doctorSelects = ['appointmentDoctor', 'medicalDoctor'];
        doctorSelects.forEach(selectId => {
            const select = document.getElementById(selectId);
            if (select) {
                select.innerHTML = '<option value="">Select Doctor</option>' +
                    this.doctors.map(doctor => 
                        `<option value="${doctor.id}">Dr. ${doctor.name} - ${doctor.specialization}</option>`
                    ).join('');
            }
        });
    }

    showNotification(message, type = 'info') {
        const notifications = document.getElementById('notifications');
        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        notification.textContent = message;
        
        notifications.appendChild(notification);
        
        setTimeout(() => {
            notification.remove();
        }, 5000);
    }
}

// Global hospital system instance
const hospitalSystem = new HospitalManagementSystem();

// Tab Management
function showTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    
    // Remove active class from all buttons
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Show selected tab
    document.getElementById(tabName).classList.add('active');
    
    // Activate corresponding button
    document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
    
    // Update form options when switching to relevant tabs
    if (['appointments', 'medical', 'billing'].includes(tabName)) {
        hospitalSystem.updateSelectOptions();
    }
}

// Form Management
function showPatientForm() {
    document.getElementById('patientForm').style.display = 'block';
}

function hidePatientForm() {
    document.getElementById('patientForm').style.display = 'none';
    document.getElementById('addPatientForm').reset();
}

function showDoctorForm() {
    document.getElementById('doctorForm').style.display = 'block';
}

function hideDoctorForm() {
    document.getElementById('doctorForm').style.display = 'none';
    document.getElementById('addDoctorForm').reset();
}

function showAppointmentForm() {
    document.getElementById('appointmentForm').style.display = 'block';
    hospitalSystem.updateSelectOptions();
}

function hideAppointmentForm() {
    document.getElementById('appointmentForm').style.display = 'none';
    document.getElementById('addAppointmentForm').reset();
}

function showMedicalForm() {
    document.getElementById('medicalForm').style.display = 'block';
    hospitalSystem.updateSelectOptions();
}

function hideMedicalForm() {
    document.getElementById('medicalForm').style.display = 'none';
    document.getElementById('addMedicalForm').reset();
}

function showBillingForm() {
    document.getElementById('billingForm').style.display = 'block';
    hospitalSystem.updateSelectOptions();
}

function hideBillingForm() {
    document.getElementById('billingForm').style.display = 'none';
    document.getElementById('generateBillForm').reset();
}

// Form Submissions
document.getElementById('addPatientForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const formData = new FormData(this);
    const patientData = {
        name: document.getElementById('patientName').value,
        age: parseInt(document.getElementById('patientAge').value),
        gender: document.getElementById('patientGender').value,
        contactNumber: document.getElementById('patientContact').value,
        email: document.getElementById('patientEmail').value,
        address: document.getElementById('patientAddress').value,
        emergencyContact: document.getElementById('emergencyContact').value,
        bloodGroup: document.getElementById('bloodGroup').value,
        allergies: document.getElementById('allergies').value
    };
    hospitalSystem.addPatient(patientData);
    hidePatientForm();
});

document.getElementById('addDoctorForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const doctorData = {
        name: document.getElementById('doctorName').value,
        specialization: document.getElementById('specialization').value,
        department: document.getElementById('department').value,
        experienceYears: parseInt(document.getElementById('experience').value),
        consultationFee: parseFloat(document.getElementById('consultationFee').value),
        qualification: document.getElementById('qualification').value,
        contactNumber: document.getElementById('doctorContact').value,
        email: document.getElementById('doctorEmail').value
    };
    hospitalSystem.addDoctor(doctorData);
    hideDoctorForm();
});

document.getElementById('addAppointmentForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const appointmentData = {
        patientId: parseInt(document.getElementById('appointmentPatient').value),
        doctorId: parseInt(document.getElementById('appointmentDoctor').value),
        date: document.getElementById('appointmentDate').value,
        timeSlot: document.getElementById('appointmentTime').value,
        description: document.getElementById('appointmentDescription').value
    };
    hospitalSystem.scheduleAppointment(appointmentData);
    hideAppointmentForm();
});

document.getElementById('addMedicalForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const medicalData = {
        patientId: parseInt(document.getElementById('medicalPatient').value),
        doctorId: parseInt(document.getElementById('medicalDoctor').value),
        diagnosis: document.getElementById('diagnosis').value,
        treatment: document.getElementById('treatment').value,
        treatmentCost: document.getElementById('treatmentCost').value,
        prescription: document.getElementById('prescription').value,
        notes: document.getElementById('medicalNotes').value
    };
    hospitalSystem.addMedicalRecord(medicalData);
    hideMedicalForm();
});

document.getElementById('generateBillForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const patientId = parseInt(document.getElementById('billPatient').value);
    hospitalSystem.generateBill(patientId);
    hideBillingForm();
});

// Additional Functions
function viewPatientDetails(patientId) {
    const patient = hospitalSystem.patients.find(p => p.id == patientId);
    if (patient) {
        alert(`Patient Details:\n\nName: ${patient.name}\nAge: ${patient.age}\nGender: ${patient.gender}\nContact: ${patient.contactNumber}\nEmail: ${patient.email}\nAddress: ${patient.address}\nBlood Group: ${patient.bloodGroup}\nAllergies: ${patient.allergies.join(', ')}\nTotal Bill: $${patient.totalBillAmount}`);
    }
}

function viewBillDetails(billId) {
    const bill = hospitalSystem.bills.find(b => b.id == billId);
    if (bill) {
        const billContent = document.getElementById('billContent');
        billContent.innerHTML = `
            <div class="bill-header">
                <h4>Bill #${bill.id}</h4>
                <p>Patient: ${bill.patient.name}</p>
                <p>Date: ${bill.billDate.split('T')[0]}</p>
                <p>Status: <span class="status-${bill.status.toLowerCase()}">${bill.status}</span></p>
            </div>
            <div class="bill-items">
                <h5>Items:</h5>
                ${bill.items.map(item => `
                    <div class="bill-item">
                        <span>${item.description}</span>
                        <span>$${item.amount}</span>
                    </div>
                `).join('')}
            </div>
            <div class="bill-total">
                <span>Total Amount:</span>
                <span>$${bill.totalAmount}</span>
            </div>
        `;
        document.getElementById('billDetails').style.display = 'block';
    }
}

function hideBillDetails() {
    document.getElementById('billDetails').style.display = 'none';
}

function showPaymentForm(billId, amount) {
    const paymentAmount = prompt(`Enter payment amount for Bill #${billId} (Total: $${amount}):`, amount);
    if (paymentAmount && !isNaN(paymentAmount)) {
        hospitalSystem.payBill(billId, parseFloat(paymentAmount));
    }
}

// Initialize the system when page loads
document.addEventListener('DOMContentLoaded', function() {
    // Set minimum date for appointment to today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('appointmentDate').min = today;
    
    hospitalSystem.showNotification('Hospital Management System loaded successfully!', 'success');
});

// Add CSS for status badges
const style = document.createElement('style');
style.textContent = `
    .status-scheduled { color: #ffc107; font-weight: bold; }
    .status-completed { color: #28a745; font-weight: bold; }
    .status-cancelled { color: #dc3545; font-weight: bold; }
    .status-generated { color: #17a2b8; font-weight: bold; }
    .status-paid { color: #28a745; font-weight: bold; }
    .status-partially_paid { color: #ffc107; font-weight: bold; }
`;
document.head.appendChild(style);