import React, { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { createCategory, Category, CategoryType } from "../../api/categoryApi";
import "./Transactions.css";

const Transactions = () => {
    const [name, setName] = useState<string>('');
    const [type, setType] = useState<CategoryType>('debit');
    const [message, setMessage] = useState<string>('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newCategory: Category = {
            id: '',
            name,
            type
        };

        try {
            const createdCategory = await createCategory(newCategory);
            setMessage(`Category created: ${createdCategory.name} of type ${type}`);
        } catch (error) {
            setMessage(`Error creating category: ${error}, ${name}-${type}`);
        }
    }

    const handleTypeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setType(e.target.value as CategoryType);
    }

    return (
        <main className="transactions-container">
            <SideBar page="transactions"/>
            <div className="transaction-content">
            <Header title="Olá, Gabriel. Você está na aba de Transações. Aqui você encontra suas despesas e receitas passadas, além de poder cadastrar novos dados de despesas e receitas."/>
                <div>
                    <h1>Transactions Page</h1>
                </div>
                <div>
                    <h2>Create Category</h2>
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label htmlFor="name">Name:</label>
                            <input
                                id="name"
                                type="text" 
                                value={name} 
                                onChange={(e) => setName(e.target.value)} 
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="category-type">Type:</label>
                            <select
                                id="category-type"
                                name="type"
                                value={type}
                                onChange={handleTypeChange}
                            >
                                <option value="debit">Debit</option>
                                <option value="credit">Credit</option>
                            </select>
                        </div>
                        <button type="submit">Create Category</button>
                    </form>
                    {message && <p>{message}</p>}
                </div>
            </div>
            
        </main>
    );
};

export { Transactions };