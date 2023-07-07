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

function userToTeamGenerator(team, master) {
    let usernameCookie = getCookieValue('username');
    const user = {
        name: usernameCookie,
        master: master
    };
    const request = {
        user: user,
        teamColor: team
    };
    addUserToTeam(request, getRoomIdFromUrl(), updateUI);

}

function addUserToTeam(request, roomId, callback) {
    stompClient.send(`/app/team/${roomId}/join`, {}, JSON.stringify(request));
}

function updateUI(room) {
    const teams = Object.values(room.teamMap);

    teams.forEach(team => {
        const teamColor = team.color;
        const members = team.members;
        console.log(members)

        const isMaster = members && members.some(member => member.master);

        const prefix = isMaster ? "m" : "n";
        const teamElementId = prefix + teamColor + "Team";

        const membersElement = document.getElementById(teamElementId);
        if (members && Array.isArray(members)) {
            members.forEach(member => {
                const memberElement = document.createElement("div");
                memberElement.className = "joiner"
                memberElement.innerText = member.name;
                membersElement.appendChild(memberElement);
            });
        }
    });
}
function createJoinerDiv(joinUser, usernameCookie){
    const joiner = document.createElement('div');
    joiner.className = 'joiner';
    joiner.innerText = usernameCookie;
    joinUser.appendChild(joiner);
}