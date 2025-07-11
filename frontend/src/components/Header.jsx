import React from 'react';

const Header = () => (
  <header className="bg-black w-full py-4 shadow-md">
    <div className="flex flex-col items-center">
      <span
        className="text-white text-3xl font-bold italic uppercase tracking-widest"
        style={{ fontFamily: 'Oswald, Arial Black, sans-serif' }}
      >
        UFC AI Predictor
      </span>
      <span className="block w-16 h-1 bg-red-600 mt-1 rounded-full"></span>
    </div>
  </header>
);

export default Header; 