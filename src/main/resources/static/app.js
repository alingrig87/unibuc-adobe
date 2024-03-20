import { products } from './products.js';

// Function to fetch products from the server
async function fetchProducts() {
	try {
		const response = await fetch('https://supreme-yodel-vr9w755547w3qp5-8080.app.github.dev/products',
		{	
			method: "GET",
			headers: 
			{
				'X-Github-Token':'ghu_Z1oNQ4sGvXWpDry6iCtbKWEqT5F3zM3kWAOlabc'
			}
		}
		);
		const data = await response.json();
		return data;
	} catch (error) {
		alert('Error fetching products:', error);
		return products;
	}
}

// Function to display products in cards
function displayProducts(products) {
	const productContainer = document.getElementById('productContainer');

	// Clear previous content
	productContainer.innerHTML = '';

	// Create and append product cards
	products.forEach((product) => {
		const productCard = document.createElement('div');
		productCard.classList.add('product-card');

		const productName = document.createElement('h2');
		productName.textContent = product.name;
		productName.classList.add('product-name');

		const productPrice = document.createElement('p');
		productPrice.textContent = `$${product.price.toFixed(2)}`;
		productPrice.classList.add('product-price');

		const productImage = document.createElement('img');
		productImage.src = product.imageURL;
		productImage.alt = product.name;
		productImage.classList.add('product-image');

		const productDescription = document.createElement('p');
		productDescription.textContent = product.description;
		productDescription.classList.add('product-description');

		productCard.appendChild(productName);
		productCard.appendChild(productPrice);
		productCard.appendChild(productImage);
		productCard.appendChild(productDescription);

		productContainer.appendChild(productCard);
	});
}

// Fetch products and display them when the page loads
document.addEventListener('DOMContentLoaded', async () => {
	const products = await fetchProducts();
	displayProducts(products);
});