import React, { useState } from 'react';
import FighterCard from './FighterCard';
import { compareFighters } from '../services/Server';

const Predictor = () => {
  const [fighter1, setFighter1] = useState(null);
  const [fighter2, setFighter2] = useState(null);
  const [compareResult, setCompareResult] = useState(null);
  const [loadingCompare, setLoadingCompare] = useState(false);

  const handleCompare = async () => {
    setLoadingCompare(true);
    setCompareResult(null);
    try {
      const res = await compareFighters(fighter1.name, fighter2.name);
      setCompareResult(res.data);
    } catch (err) {
      // Show backend error message if available
      const errorMsg =
        err.response && err.response.data
          ? typeof err.response.data === 'string'
            ? err.response.data
            : err.response.data.message || "Comparison failed."
          : "Comparison failed.";
      setCompareResult({ error: errorMsg });
    }
    setLoadingCompare(false);
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-[80vh]">
      <div className="bg-white bg-opacity-80 rounded-xl shadow-2xl p-8 w-full max-w-4xl mt-8">
        <h2 className="text-2xl font-bold text-center mb-6 text-gray-900">Compare UFC Fighters</h2>
        <div className="flex flex-row justify-center gap-8">
          <FighterCard label="Fighter 1" onFighterSelect={setFighter1} />
          <FighterCard label="Fighter 2" onFighterSelect={setFighter2} />
        </div>
        <div className="flex justify-center mt-8">
          <button
            className="px-8 py-3 bg-red-600 text-white font-bold rounded-lg shadow-md disabled:opacity-50 disabled:cursor-not-allowed transition"
            disabled={!fighter1 || !fighter2 || loadingCompare}
            onClick={handleCompare}
          >
            {loadingCompare ? 'Comparing...' : 'Compare'}
          </button>
        </div>
        {compareResult && !compareResult.error && (
          <div className="mt-8 mx-auto max-w-2xl bg-gray-100 rounded-lg p-6 shadow text-center">
            <div className="text-2xl font-bold mb-2">
              Winner: <span className="text-red-600">{compareResult.winner}</span> <span className="text-lg font-normal">({compareResult.probability})</span>
            </div>
            <div className="mb-2 text-lg font-semibold">Outcome: {compareResult.outcome}</div>
            <ul className="text-left mt-4">
              {compareResult.reasons && compareResult.reasons.map((reason, idx) => (
                <li key={idx} className="mb-1">- {reason}</li>
              ))}
            </ul>
          </div>
        )}
        {compareResult && compareResult.error && (
          <div className="mt-8 text-center text-red-600">{compareResult.error}</div>
        )}
      </div>
    </div>
  );
};

export default Predictor; 