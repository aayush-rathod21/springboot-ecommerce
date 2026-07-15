
import React from 'react';

const CategoryList = ({ categories, onSelectCategory }) => {
  return (
    <div className="category-list">
      <h2>Categories</h2>
      <ul>
        <li onClick={() => onSelectCategory(null)}>All</li>
        {categories.map((category) => (
          <li key={category} onClick={() => onSelectCategory(category)}>
            {category}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CategoryList;
