<?php

/* Connecting to MySQL server */
$servername = "10.25.71.66";
$username = "dbu309grp29";
$password = "xuXRPfjcFFH";

// Create connection to server
$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
//echo "Connected successfully" . "<br>";

//Connect to database
mysqli_select_db($conn, 'db309grp29'); 
if (mysqli_connect_errno()) {
	echo "Could not connect to database";
	exit();
}

/* ------------------------------------------ */

$infoNum = 0;
$budget = array();
$origin = array();
$forigin = array();
$dest = array();
$fdest = array();
$selectedPOIs = array();
$numPeople = array();
$departDate = array();
$fdepartDate = array();
$returnDate = array();
$freturnDate = array();
$departTime = array();
$arrivalTime = array();
$flightNum = array();
$carrier = array();
$price = array();
$placeName = array();
$placeAddress = array();
$numEntries = 0;
$col_num = 0;
$flight_col_num = 0;
$places_col_num = 0;
$budget_num = 0;
$origin_num = 0;
$forigin_num = 0;
$dest_num = 0;
$fdest_num = 0;
$child_num = 0;
$people_num = 0;
$poi_num = 0;
$depart_num = 0;
$fdepart_num = 0;
$return_num = 0;
$freturn_num = 0;
$departTime_num = 0;
$arrivalTime_num = 0;
$flight_num = 0;
$carrier_num = 0;
$price_num = 0;
$placeName_num = 0;
$placeAddress_num = 0;

//Fetch user input data from database
$query = "SHOW COLUMNS FROM params";
$result = mysqli_query($conn, $query);
while ($row = mysqli_fetch_array($result)) {
	$columns[$col_num] = $row['Field'];
	$col_num++;
}

//Loop through columns in the params table to sort data into arrays
$col_size = sizeof($columns);
for ($col = 0; $col < $col_size; $col++) {
	$query = "SELECT " . $columns[$col] . " FROM params";
	$result = mysqli_query($conn, $query);
	$num_rows = mysqli_num_rows($result);
//if num_rows is 0...	
	//echo $num_rows . "<br>";
//echo $columns[$col] . "<br>";
	switch ($columns[$col]) {
		case "budget":
			while ($row = $result->fetch_assoc()) {
				$budget[$budget_num] = $row["budget"];
				//echo $budget[$budget_num] . "<br>";
				$budget_num++;
			}
			break;
		case "origin":
			while ($row = $result->fetch_assoc()) {
				$origin[$origin_num] = $row["origin"];
				//echo $origin[$origin_num] . "<br>";
				$origin_num++;
			}
			break;
		case "destination":
			while ($row = $result->fetch_assoc()) {
				$dest[$dest_num] = $row["destination"];
				//echo $dest[$dest_num] . "<br>";
				$dest_num++;
			}
			break;
		case "children":
			while ($row = $result->fetch_assoc()) {
				$children[$child_num] = $row["children"];
				//echo $children[$child_num] . "<br>";
				$child_num++;
			}
			break;
		case "pointsOfInterest":
			while ($row = $result->fetch_assoc()) {
				$selectedPOIs[$poi_num] = $row["pointsOfInterest"];
				//echo $selectedPOIs[$poi_num] . "<br>";
				$poi_num++;
			}
			break;
		case "numPeople":
			while ($row = $result->fetch_assoc()) {
				$numPeople[$people_num] = $row["numPeople"];
				//echo $numPeople[$people_num] . "<br>";
				$people_num++;
			}
			break;
		case "departDate":
			while ($row = $result->fetch_assoc()) {
				$departDate[$depart_num] = $row["departDate"];
				//echo $departDate[$depart_num] . "<br>";
				$depart_num++;
			}
			break;
		case "returnDate":
			while ($row = $result->fetch_assoc()) {
				$returnDate[$return_num] = $row["returnDate"];
				//echo $returnDate[$return_num] . "<br>";
				$return_num++;
			}
			break;	
	}
}

//Fetch flight results from database
$query = "SHOW COLUMNS FROM flights";
$result = mysqli_query($conn, $query);
while ($row = mysqli_fetch_array($result)) {
	$flight_columns[$flight_col_num] = $row['Field'];
	$flight_col_num++;
}

//Loop through columns in the flights table to sort data into arrays
$flight_col_size = sizeof($flight_columns);
for ($flight_col = 0; $flight_col < $flight_col_size; $flight_col++) {
	$query = "SELECT " . $flight_columns[$flight_col] . " FROM flights";
	$result = mysqli_query($conn, $query);
	$num_rows = mysqli_num_rows($result);
//if num_rows is 0...	
	//echo $num_rows . "<br>";
//echo $flight_columns[$flight_col] . "<br>";
	switch ($flight_columns[$flight_col]) {
		case "origin":
			while ($row = $result->fetch_assoc()) {
				$forigin[$forigin_num] = $row["origin"];
				//echo $forigin[$forigin_num] . "<br><br>";
				$forigin_num++;
				//echo $forigin_num . "<br>";
			}
			break;
		case "destination":
			while ($row = $result->fetch_assoc()) {
				$fdest[$fdest_num] = $row["destination"];
				//echo $fdest[$fdest_num] . "<br>";
				$fdest_num++;
			}
			break;
		case "departDate":
			while ($row = $result->fetch_assoc()) {
				$fdepartDate[$fdepartDate_num] = $row["departDate"];
				//echo $fdepartDate[$fdepartDate_num] . "<br>";
				$fdepart_num++;
			}
			break;
		case "departTime":
			while ($row = $result->fetch_assoc()) {
				$departTime[$departTime_num] = $row["departTime"];
				//echo $departTime[$departTime_num] . "<br>";
				$departTime_num++;
			}
			break;
		case "arrivalTime":
			while ($row = $result->fetch_assoc()) {
				$arrivalTime[$arrivalTime_num] = $row["arrivalTime"];
				//echo $arrivalTime[$arrivalTime_num] . "<br>";
				$arrivalTime_num++;
			}
			break;
		case "flightNum":
			while ($row = $result->fetch_assoc()) {
				$flightNum[$flight_num] = $row["flightNum"];
				//echo $flightNum[$flight_num] . "<br>";
				$flight_num++;
			}
			break;
		case "carrier":
			while ($row = $result->fetch_assoc()) {
				$carrier[$carrier_num] = $row["carrier"];
				//echo $carrier[$carrier_num] . "<br>";
				$carrier_num++;
			}
			break;
		case "price":
			while ($row = $result->fetch_assoc()) {
				$price[$price_num] = $row["price"];
				//echo $price[$price_num] . "<br>";
				$price_num++;
			}
			break;
		case "returnDate":
			while ($row = $result->fetch_assoc()) {
				$freturnDate[$freturn_num] = $row["returnDate"];
				//echo $freturnDate[$freturn_num] . "<br>";
				$freturn_num++;
			}
			break;	
	}
}

//Fetch place results from database
$query = "SHOW COLUMNS FROM places";
$result = mysqli_query($conn, $query);
while ($row = mysqli_fetch_array($result)) {
	$places_columns[$places_col_num] = $row['Field'];
	$places_col_num++;
}

//Loop through columns in the places table to sort data into arrays
$places_col_size = sizeof($places_columns);
for ($places_col = 0; $places_col < $places_col_size; $places_col++) {
	$query = "SELECT " . $places_columns[$places_col] . " FROM places";
	$result = mysqli_query($conn, $query);
	$num_rows = mysqli_num_rows($result);	
	//echo $num_rows . "<br>";
	switch ($places_columns[$places_col]) {
		case "name":
			while ($row = $result->fetch_assoc()) {
				$placeName[$placeName_num] = $row["name"];
				//echo $placeName[$placeName_num] . "<br><br>";
				$placeName_num++;
				//echo $placeName_num . "<br>";
			}
			break;
		case "address":
			while ($row = $result->fetch_assoc()) {
				$placeAddress[$placeAddress_num] = $row["address"];
				if (strcmp($placeAddress[$placeAddress_num], "") == 0) $placeAddress[$placeAddress_num] = "N/A";
				//echo $placeAddress[$placeAddress_num] . "<br>";
				$placeAddress_num++;
			}
			break;
	}
}
?>

<html>

<script type="text/javascript">
var num_boxes = <?php echo $budget_num ?>;
var infoBoxPos = 130;
var historyBoxPos = 130;
var box = 0;
var hbox = 0;

//Dynamically creates an info box that contains user information from the MySQL database. 
//Each box corresponds to one row in the database.
function infoHistoryBox() {
	for (box = 0; box < num_boxes; box++) {
		var budget = <?php echo json_encode($budget) ?>;
		var origin = <?php echo json_encode($origin) ?>;
		var dest = <?php echo json_encode($dest) ?>;
		var departDate = <?php echo json_encode($departDate) ?>;
		var returnDate = <?php echo json_encode($returnDate) ?>;
		var numPeople = <?php echo json_encode($numPeople) ?>;
		var children = <?php echo json_encode($children) ?>;
		var selectedPOIs = <?php echo json_encode($selectedPOIs) ?>;
	
		var div = document.createElement("div");
			div.className = "shadowEffect";
			div.style.backgroundColor = "#FFFFFF"; 
			div.style.position = "absolute"; 
			div.style.top = infoBoxPos + "px"; 
			div.style.left = "20px"; 
			div.style.height = "215px"; 
			div.style.width = "600px";
		
		var table = document.createElement("table");
			var tbody = document.createElement("tbody");
			var tr1 = document.createElement("tr");
				var budgetCol = document.createElement("th");
					budgetCol.innerHTML = "Budget: ";
					var input = document.createElement("input");
					input.type = "text";
					input.name = "budget" + box;
					input.placeholder = budget[box];
					input.disabled = true;
					budgetCol.appendChild(input);
				var originCol = document.createElement("th");
					originCol.innerHTML = "Origin: ";
					input = document.createElement("input");
					input.type = "text";
					input.name = "origin" + box;
					input.placeholder = origin[box];
					input.disabled = true;
					originCol.appendChild(input);
				var destCol = document.createElement("th");
					destCol.innerHTML = "Destination: ";
					input = document.createElement("input");
					input.type = "text";
					input.name = "dest" + box;
					input.placeholder = dest[box];
					input.disabled = true;
					destCol.appendChild(input);
			tr1.appendChild(budgetCol); tr1.appendChild(originCol); tr1.appendChild(destCol);
			var tr2 = document.createElement("tr");
				var departCol = document.createElement("th");
					departCol.innerHTML = "Departure Date: ";
					var input = document.createElement("input");
					input.type = "text";
					input.name = "depart" + box;
					input.placeholder = departDate[box];
					input.disabled = true;
					departCol.appendChild(input);
				var returnCol = document.createElement("th");
					returnCol.innerHTML = "Return date: ";
					var input = document.createElement("input");
					input.type = "text";
					input.name = "return" + box;
					input.placeholder = returnDate[box];
					input.disabled = true;
					returnCol.appendChild(input);
				var numPeopleCol = document.createElement("th");
					numPeopleCol.innerHTML = "Number of People: ";
					var input = document.createElement("input");
					input.type = "text";
					input.name = "numPeople" + box;
					input.placeholder = numPeople[box];
					input.disabled = true;
					numPeopleCol.appendChild(input);
			tr2.appendChild(departCol); tr2.appendChild(returnCol); tr2.appendChild(numPeopleCol);
			var tr3 = document.createElement("tr");
				var childrenCol = document.createElement("th");
					childrenCol.innerHTML = "Children: ";
					var input = document.createElement("input");
					input.type = "text";
					input.name = "children" + box;
					input.placeholder = children[box];
					input.disabled = true;
					childrenCol.appendChild(input);
				var selectedPOIsCol = document.createElement("th");
					selectedPOIsCol.innerHTML = "Selected POIs: ";
					var input = document.createElement("input");
					input.type = "text";
					input.name = "selectedPOIs" + box;
					input.placeholder = selectedPOIs[box];
					input.disabled = true;
					selectedPOIsCol.appendChild(input);
			tr3.appendChild(childrenCol); tr3.appendChild(selectedPOIsCol);
			tbody.appendChild(tr1); tbody.appendChild(tr2); tbody.appendChild(tr3);
		table.appendChild(tbody);			
		div.appendChild(table);
		document.body.appendChild(div);
		infoBoxPos = infoBoxPos + 240;
	}
}

var fnum_boxes = <?php echo $forigin_num ?>;

function resultsHistoryBox() {
	for (hbox = 0; hbox < fnum_boxes; hbox++) {
		var flight = "Flight option " + hbox;
			var forigin = <?php echo json_encode($forigin) ?>;
			var fdest = <?php echo json_encode($fdest) ?>;
			var fdepartDate = <?php echo json_encode($fdepartDate) ?>;
			var freturnDate = <?php echo json_encode($freturnDate) ?>;
			var departTime = <?php echo json_encode($departTime) ?>;
			var arrivalTime = <?php echo json_encode($arrivalTime) ?>;
			var flightNum = <?php echo json_encode($flightNum) ?>;
			var carrier = <?php echo json_encode($carrier) ?>;
			var price = <?php echo json_encode($price) ?>;
		var accommo = "Accommo option " + hbox;
			var acName = "N/A";
			var acAddress = "N/A";
			var acPrice = "N/A";
		var poi = "Point of interest " + hbox;
			var placeName = <?php echo json_encode($placeName) ?>;
			var placeAddress = <?php echo json_encode($placeAddress) ?>;
		var div = document.createElement("div");
			div.className = "shadowEffect";
			div.style.backgroundColor = "#FFFFFF"; 
			div.style.position = "absolute"; 
			div.style.top = historyBoxPos + "px";
			div.style.left = "650px"; 
			div.style.height = "215px"; 
			div.style.width = "800px";
		var table = document.createElement("table");
			var tbody = document.createElement("tbody");
			var tr1 = document.createElement("tr");
				var flightCol = document.createElement("th");
					flightCol.innerHTML = "Flights: ";
					var input = document.createElement("textarea");
					input.cols = 30;
					input.rows = 9;
					input.innerHTML = "Origin: " + forigin[hbox] + '\n'	
									+ "Destination: " + fdest[hbox] + '\n' 
									+ "Departure Date: " + fdepartDate[hbox] + '\n' 
									+ "Return Date: " + freturnDate[hbox] + '\n'	
									+ "Departure Time: " + departTime[hbox] + '\n' 
									+ "Arrival Time: " + arrivalTime[hbox] + '\n' 
									+ "Flight number: " + flightNum[hbox] + '\n' 
									+ "Airline Carrier: " + carrier[hbox] + '\n' 
									+ "Price: " + price[hbox];
					input.name = "flight" + hbox;
					input.disabled = true;
					flightCol.appendChild(input);
				var accommoCol = document.createElement("th");
					accommoCol.innerHTML = "Accommodations: ";
					var input = document.createElement("textarea");
					input.cols = 15;
					input.rows = 5;
					input.innerHTML = "Name: " + acName + '\n'	
									+ "Address: " + acAddress + '\n' 
									+ "Price: " + acPrice;
					input.name = "accommo" + hbox;
					input.disabled = true;
					accommoCol.appendChild(input);
				var poiCol = document.createElement("th");
					poiCol.innerHTML = "Points of Interest: ";
					var input = document.createElement("textarea");
					input.cols = 30;
					input.rows = 5;
					input.innerHTML = "Name: " + placeName[hbox] + '\n'	
									+ "Address: " + placeAddress[hbox];
					input.name = "place" + hbox;
					input.disabled = true;
					poiCol.appendChild(input);
				tr1.appendChild(flightCol); tr1.appendChild(accommoCol); tr1.appendChild(poiCol);
				tbody.appendChild(tr1);
			table.appendChild(tbody);			
			div.appendChild(table);
			document.body.appendChild(div);
			historyBoxPos = historyBoxPos + 240;
	}
}

</script>

<style type="text/css">
body {
	background: #72BCDB;
}

.header {
	text-align:center;
	position:absolute;
	height:60px;
	width:99%;
	background-color:#FFFFFF
}

.footer {
	position:absolute;
	bottom: 0%;
	height:30px;
	width:100%;
	background-color:#FFFFFF
}
.shadowEffect {
	box-shadow: 0 10px 6px -6px #777;
}
</style>

<head>
	<title> TAPP Server History </title>
</head>

<div class="header">
	<font size="30px"> TAPP Server History </font>
</div>

<h1 style="position:absolute; top:65px; left:230px;"> Information </h1> 
<h1 style="position:absolute; top:65px; left:890px;"> Results </h1>

<body onload="infoHistoryBox(); resultsHistoryBox();">

</body>

</html>