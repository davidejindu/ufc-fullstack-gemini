import React from 'react';

const FighterList = ({ fighters, selectedFighter1, selectedFighter2, onSelect }) => {
  return (
    <ul className="max-h-48 overflow-y-auto mb-4">
      {fighters.map(f => {
        const isSelected =
          (selectedFighter1 && f.id === selectedFighter1.id) ||
          (selectedFighter2 && f.id === selectedFighter2.id);
        return (
          <li
            key={f.id}
            className={`cursor-pointer px-4 py-2 rounded-lg transition mb-1 text-gray-900 font-medium ${isSelected ? 'bg-red-100' : 'hover:bg-red-50'}`}
            onClick={() => onSelect(f)}
          >
            {f.name}
          </li>
        );
      })}
      {fighters.length === 0 && (
        <li className="text-gray-500 text-center">No fighters found.</li>
      )}
    </ul>
  );
};

export default FighterList; 