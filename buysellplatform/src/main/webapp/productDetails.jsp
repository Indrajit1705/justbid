<%@ page import="com.buysellplatform.model.Product" %>
<html>
<head>
    <title>Product Details</title>
</head>
<body>
    <%
        Product product = (Product) request.getAttribute("product");
        if (product != null) {
    %>
        <h1><%= product.getTitle() %></h1>
        <img src="<%= product.getImage() %>" alt="<%= product.getTitle() %>"/>
        <p>Description: <%= product.getDescription() %></p>
        <p>Minimum Bid Price: $<%= product.getMinBidPrice() %></p>
        <p>Current Bid Price: $<%= product.getCurrentBidPrice() %></p>
        <p>Auction End Date: <%= product.getAuctionEndDate() %></p>
    <%
        } else {
    %>
        <p>No product details available.</p>
    <%
        }
    %>
</body>
</html>
