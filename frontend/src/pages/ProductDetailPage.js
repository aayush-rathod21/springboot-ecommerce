
import React from 'react';
import { useParams } from 'react-router-dom';
import { products } from '../data/mockData';

const ProductDetailPage = () => {
  const { id } = useParams();
  const product = products.find((p) => p.id === parseInt(id));

  if (!product) {
    return <div>Product not found! This is where you might show an error message if the backend was not connected.</div>;
  }

  return (
    <div className="product-detail-page">
      <div className="product-detail-image">
        <img src={product.imageUrl} alt={product.name} />
      </div>
      <div className="product-detail-info">
        <h1>{product.name}</h1>
        <p className="price">${product.price}</p>
        <p className="description">{product.description}</p>
        <p><strong>Brand:</strong> {product.brand}</p>
        <p><strong>Category:</strong> {product.category}</p>
        <p><strong>Release Date:</strong> {product.releaseDate}</p>
        <p>{product.available ? 'In Stock' : 'Out of Stock'}</p>
        <p><strong>Quantity Available:</strong> {product.quantity}</p>
      </div>
    </div>
  );
};

export default ProductDetailPage;
