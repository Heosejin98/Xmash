import { useState } from 'react'
import './App.css'
import { Button } from '@/shared/ui/button'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
    {count}
    <Button onClick={() => setCount(count + 1)}>Increment</Button>
    </>
  )
}

export default App
