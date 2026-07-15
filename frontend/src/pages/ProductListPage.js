
import React, { useState, useEffect } from 'react';
import { products, categories } from '../data/mockData';
import ProductCard from '../components/ProductCard';
import SearchBar from '../components/SearchBar';
import CategoryList from '../components/CategoryList';

const ProductListPage = () => {
  const [filteredProducts, setFilteredProducts] = useState(products);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    let result = products;

    if (searchTerm) {
      result = result.filter((product) =>
        product.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    if (selectedCategory) {
      result = result.filter(
        (product) => product.category === selectedCategory
      );
    }

    setFilteredProducts(result);
  }, [searchTerm, selectedCategory]);

  return (
    <div className="product-list-page">
      <div className="sidebar">
        <CategoryList
          categories={categories}
          onSelectCategory={setSelectedCategory}
        />
      </div>
      <div className="main-content">
        <SearchBar onSearch={setSearchTerm} />
        <div className="product-grid">
          {filteredProducts.length > 0 ? (
            filteredProducts.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))
          ) : (
            <p>No products found. Try adjusting your search or filters.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductListPage;
