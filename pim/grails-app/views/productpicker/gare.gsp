<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />

</head>
<body>



	<form id="addtocart" action="http://www.gare.com/v3/shop_cart.cfm"  method="post">
			<input type="hidden" name="CategoryID" value="0" /> 

			<input type="hidden" name="SpecialProductCat" value="" /> 
		<input type="hidden" name="NTAForm" value="CartUpdate" /> 
		<input
			type="hidden" name="ProductQTY" value="${ProductQTY}" /> 
			<input
			type="hidden" name="ProductID" value="${ProductID}" /> 
			
			<input
			type="hidden" name="ShopAction" value="AddItem" /> <input
			type="submit" />
	</form>

<script>

$('#addtocart').submit();


</script>


</body>
</html>
