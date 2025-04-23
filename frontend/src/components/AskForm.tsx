import React, { useState } from 'react';

const AskForm: React.FC = () => {
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await fetch(`/api/ask?question=${encodeURIComponent(question)}`);
    const data = await response.json();
    setAnswer(data.answer);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask your question..."
        />
        <button type="submit">Ask</button>
      </form>
      {answer && <p>Answer: {answer}</p>}
    </div>
  );
};

export default AskForm;