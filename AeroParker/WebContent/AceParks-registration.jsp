<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AceParks Registration</title>
</head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<body style="background-color: #E6E6FA; text-align: center;">
	<h1>AceParks</h1>


				<form action="Register" method="post">
				<div class="row justify-content-center">
					<table>
						<tr>
							<td>E-Mail</td>
							<td><input type="email" name="email" maxlength="255"
								required></td>
						</tr>
						<tr>
							<td>Title</td>
							<td><input type="text" name="title" maxlength="5" required></td>
						</tr>
						<tr>
							<td>First Name</td>
							<td><input type="text" name="fname" maxlength="50" required></td>
						</tr>
						<tr>
							<td>Last Name</td>
							<td><input type="text" name="lname" maxlength="50" required></td>
						</tr>
						<tr>
							<td>Address Line 1</td>
							<td><input type="text" name="address1" maxlength="255"
								required></td>
						</tr>
						<tr>
							<td>Address Line 2</td>
							<td><input type="text" name="address2" maxlength="255"></td>
						</tr>
						<tr>
							<td>City</td>
							<td><input type="text" name="city" maxlength="255"></td>
						</tr>
						<tr>
							<td>Postcode</td>
							<td><input type="text" name="postcode" maxlength="10"
								required></td>
						</tr>
						<tr>
							<td>Telephone Number</td>
							<td><input type="text" name="number" maxlength="20"></td>
						</tr>
						<tr>
							<td>Site</td>
							<td><select name="site">
									<option>AceParks</option>
							</select></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Register"></td>
						</tr>
					</table>
					</div>
				</form>

</body>
</html>