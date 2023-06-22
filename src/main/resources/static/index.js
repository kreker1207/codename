window.onload = async function () {
    let cookie = document.cookie
    console.log(cookie)
    let roomIdCookie = getCookieValue('roomId');
    let usernameCookie = getCookieValue('username')
    if (usernameCookie === "" || roomIdCookie === "") {
        let person = prompt("Please enter your username:", "user");
        if (person == null || person === "") {
            document.cookie = "username=user";
            await handleRoom();
        } else {
            document.cookie = "username=" + person;
            await handleRoom();
        }
    }
    connect();
};
let stompClient = null;

function connect() {
    const socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/greetings', function (generateCards) {
            wordShuffle(JSON.parse(generateCards.body));
        });
    });
}
function wordShuffle(response) {
    const field = document.getElementById("word-field");
    field.innerHTML = '';

    let orange = 0;
    let blue = 0;

    response.forEach((item) => {
        const wordCard = document.createElement('div');
        wordCard.className = 'word';
        wordCard.innerText = item.word;
        wordCard.dataset.color = item.cardType;
        field.appendChild(wordCard);
        item.cardType === 'BLUE' ? blue++ : item.cardType === 'ORANGE' ? orange++ : 0;
    });
    cardNum('orange', orange);
    cardNum('blue', blue);
}

function cardNum(type, num){
    console.log(type + " " + num)
    const side = document.getElementById("n"+type)
    side.innerText='';
    side.innerText = num
}
function generateCards() {
    stompClient.send('/app/cards', {}, '');
}

function createRoom() {
    return fetch('/roomId', {
        method: 'POST'
    })
        .then(response => response.text())
        .catch(error => {
            console.error('Error:', error);
        });
}

async function handleRoom() {
    try {
        let roomId = await createRoom();
        console.log(roomId);
        document.cookie = "roomId=" + roomId;
    } catch (error) {
        console.error('Error:', error);
    }
}

function getCookieValue(name) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
        if (cookie.startsWith(name + '=')) {
            return cookie.substring(name.length + 1);
        }
    }
    return "";
}