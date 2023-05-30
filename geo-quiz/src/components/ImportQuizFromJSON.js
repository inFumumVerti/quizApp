import React, { useEffect, useState } from 'react';

const ImportQuizFromJSON = ({ importQuizJson, setImportQuizJson, handleQuizSubmit }) => {

  const [jsonInput, setJsonInput] = useState("");

  // Define a function to handle file input
  const handleFileChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        try {
          const json = JSON.parse(event.target.result);
          setImportQuizJson(json);
        } catch (error) {
          console.error('Invalid JSON file.');
        }
      };
      reader.readAsText(file);
    }
  };

  const handleInputChange = (event) => {
    setJsonInput(event.target.value);
  };

   useEffect(() => {
      try {
        const json = JSON.parse(jsonInput);
        setImportQuizJson(json);
      } catch (error) {
        console.error('Invalid JSON.');
      }
    }, [jsonInput]);

  return (
    <div className="import-json">
      <h2>Import Quiz from JSON</h2>
      <div>
        <input
          type="file"
          id="file"
          accept=".json"
          onChange={handleFileChange}
        />
        <textarea
          value={jsonInput}
          onChange={handleInputChange}
          placeholder="Enter JSON here"
        />
        <button onClick={handleQuizSubmit} disabled={!importQuizJson}>
          Submit
        </button>
      </div>
    </div>
  );
};

export default ImportQuizFromJSON;
