// mouse-tracker.js

let lastMoveTime = Date.now();
let hoverDuration = 0;
let targetElement = null;

// Function to track mouse movements
function trackMouse() {
    let mouseData = {
        date: new Date().toISOString(),  // Timestamp
        hoverDuration: "PT0S",           // Default duration in ISO 8601 format
        x: 0,
        y: 0,
        button: "",                      // e.g., "left", "right", "middle"
        eventType: "move",               // Default event type
        targetElement: "",                // Element where the event happens
        targetElementText: ""            
    };

    // Mouse movement listener
    document.addEventListener('mousemove', (event) => {
        let currentTime = Date.now();
        hoverDuration = (currentTime - lastMoveTime) / 1000;  // Calculate hover duration in seconds
        lastMoveTime = currentTime;

        mouseData.x = event.clientX;
        mouseData.y = event.clientY;
        mouseData.date = new Date().toISOString();
        mouseData.hoverDuration = `PT${hoverDuration}S`;  // ISO 8601 duration format
        mouseData.eventType = "move";
        mouseData.targetElement = event.target.tagName.toLowerCase();
        mouseData.targetElementText = event.target.textContent;
    });

    // Mouse click listener
    document.addEventListener('click', (event) => {
        mouseData.x = event.clientX;
        mouseData.y = event.clientY;
        mouseData.date = new Date().toISOString();
        mouseData.eventType = "click";
        mouseData.button = getMouseButton(event);
        mouseData.targetElement = event.target.tagName.toLowerCase();

        sendMouseData(mouseData);
    });

    // Send mouse data every 500ms
    setInterval(() => {
        sendMouseData(mouseData);
    }, 500);
}

// Function to send data to the server
function sendMouseData(data) {
    var link = 'http://localhost:8082/analytics'
    fetch(link, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .catch((error) => {
        console.error('Error:', error);
    })
    .then(data => console.log('Data sent successfully:', data));
}

// Helper function to get the mouse button name
function getMouseButton(event) {
    switch (event.button) {
        case 0: return 'left';
        case 1: return 'middle';
        case 2: return 'right';
        default: return '';
    }
}

// Start tracking
trackMouse();
