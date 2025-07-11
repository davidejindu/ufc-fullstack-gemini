import React, { useEffect, useState } from 'react';
import { getAllDivisions, getFighersByDivison } from '../services/Server';

const FighterCard = ({ onFighterSelect, label }) => {
  const [divisions, setDivisions] = useState([]);
  const [selectedDivision, setSelectedDivision] = useState('');
  const [fighters, setFighters] = useState([]);
  const [search, setSearch] = useState('');
  const [filteredFighters, setFilteredFighters] = useState([]);
  const [selectedFighter, setSelectedFighter] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getAllDivisions().then(res => {
      setDivisions(res.data);
    });
  }, []);

  useEffect(() => {
    if (selectedDivision) {
      setLoading(true);
      getFighersByDivison(selectedDivision).then(res => {
        setFighters(res.data);
        setFilteredFighters(res.data);
        setLoading(false);
      });
    } else {
      setFighters([]);
      setFilteredFighters([]);
    }
    setSearch('');
    setSelectedFighter(null);
    if (onFighterSelect) onFighterSelect(null);
  }, [selectedDivision]);

  useEffect(() => {
    if (search.trim() === '') {
      setFilteredFighters(fighters);
    } else {
      setFilteredFighters(
        fighters.filter(f =>
          f.name.toLowerCase().includes(search.toLowerCase())
        )
      );
    }
  }, [search, fighters]);

  const handleSelectFighter = (fighter) => {
    setSelectedFighter(fighter);
    if (onFighterSelect) onFighterSelect(fighter);
  };

  return (
    <div className="flex flex-col items-center bg-gray-100 rounded-lg p-6 shadow-lg animate-fade-in w-full max-w-xs">
      {label && <div className="text-center font-bold text-gray-700 mb-2">{label}</div>}
      {/* Division Dropdown, Search, and Fighter List in a fixed-height container */}
      <div className="w-full flex flex-col h-64 mb-4">
        <select
          className="w-full p-3 mb-4 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-red-500 text-gray-800"
          value={selectedDivision}
          onChange={e => setSelectedDivision(e.target.value)}
        >
          <option value="">Select Division...</option>
          {divisions.map(div => (
            <option key={div} value={div}>{div}</option>
          ))}
        </select>
        {selectedDivision && (
          <input
            type="text"
            className="w-full p-3 mb-4 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-red-500 text-gray-800"
            placeholder="Search fighter by name..."
            value={search}
            onChange={e => setSearch(e.target.value)}
          />
        )}
        {loading ? (
          <div className="text-center text-gray-500 flex-1 flex items-center justify-center">Loading fighters...</div>
        ) : (
          <ul className="flex-1 overflow-y-auto">
            {filteredFighters.map(f => (
              <li
                key={f.id}
                className={`cursor-pointer px-4 py-2 rounded-lg transition mb-1 text-gray-900 font-medium ${selectedFighter && f.id === selectedFighter.id ? 'bg-red-100' : 'hover:bg-red-50'}`}
                onClick={() => handleSelectFighter(f)}
              >
                {f.name}
              </li>
            ))}
            {selectedDivision && !loading && filteredFighters.length === 0 && (
              <li className="text-gray-500 text-center">No fighters found.</li>
            )}
          </ul>
        )}
      </div>
      {/* Fighter Display at the bottom */}
      {selectedFighter && (
        <div className="flex flex-col items-center">
          <img
            src={selectedFighter.imageUrl && selectedFighter.imageUrl.trim()}
            alt={selectedFighter.name}
            className="w-64 h-64 object-contain rounded-full border-4 border-red-500 shadow-md bg-white mb-4"
            onError={e => {
              e.target.onerror = null;
              e.target.src = "https://via.placeholder.com/256?text=No+Image";
            }}
          />
          <div className="text-xl font-bold text-gray-900 mb-2">{selectedFighter.name}</div>
        </div>
      )}
    </div>
  );
};

export default FighterCard; 