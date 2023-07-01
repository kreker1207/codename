window.onload = async function () {
    let roomIdCookie = getCookieValue('roomId');
    let usernameCookie = getCookieValue('username');
    let urlRoomId = getRoomIdFromUrl();

    if (urlRoomId) {
        roomIdCookie = urlRoomId;
        document.cookie = "roomId=" + roomIdCookie;
    }

    if (usernameCookie === "" || roomIdCookie === "") {
        let person = prompt("Please enter your username:", "user");
        if (person == null || person === "") {
            let randomName = (Math.random() + 1).toString(36).substring(7);
            document.cookie = "username=user-" + randomName;
        } else {
            document.cookie = "username=" + person;
        }
        await handleRoom();
    }
    let roomId = getCookieValue('roomId');

    window.history.pushState({}, document.title, "?roomId=" + roomId);

    connect(roomId);
};

let stompClient = null;

function connect(roomId) {
    const socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/team/' + roomId, function (teamUpdate) {
            const room = JSON.parse(teamUpdate.body);
            updateUI(room);
        });
        stompClient.subscribe('/topic/greetings/' + roomId, function (generateCards) {
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

function cardNum(type, num) {
    console.log(type + " " + num)
    const side = document.getElementById("n" + type)
    side.innerText = '';
    side.innerText = num
}

function generateCards() {
    let urlRoomId = getRoomIdFromUrl();
    stompClient.send('/app/cards/' + urlRoomId, {}, '');
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

function getRoomIdFromUrl() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get("roomId");
}

function userToTeamGenerator(team, master, roomId) {
    let usernameCookie = getCookieValue('username');
    const user = {
        username: usernameCookie,
        master: master
    };
    const request = {
        user: user,
        teamColor: team.color
    };
    addUserToTeam(request, roomId, updateUI);
}

function addUserToTeam(request, roomId, callback) {
    fetch('/team/' + roomId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
    })
        .then(response => response.json())
        .then(room => {
            console.log(room);
            callback(room);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function updateUI(room) {
   }