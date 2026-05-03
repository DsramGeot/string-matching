import matplotlib.pyplot as plt

def parse_and_plot(filename):
    results = []
    current_run = {}

    
    try:
        with open(filename, 'r') as file:
            for line in file:
                line = line.strip()
                
                if not line:
                    if current_run:
                        results.append(current_run)
                        current_run = {}
                    continue
                
                if '#' in line:
                    parts = line.split('#')
                    data = parts[0].strip()
                    label = parts[1].strip()
                    
                    if "BruteForce" in label:
                        algo_map = {'0': 'Brute-Force', '1': 'Horspool', '2': 'Boyer-Moore'}
                        current_run['Algorithm'] = algo_map.get(data, 'Unknown')
                    elif "Key searched" in label:
                        current_run['Pattern'] = data
                        current_run['Pattern Length'] = len(data)
                    elif "File searched" in label:
                        current_run['File'] = data
                    elif "Occurance" in label:
                        current_run['Occurrences'] = int(data)
                    elif "Comparison" in label:
                        current_run['Comparisons'] = int(data)
                    elif "Runtime" in label:
                        current_run['Runtime'] = int(data)
                    elif "Memory" in label:
                        data = data.replace(',', '.')
                        current_run['Memory'] = float(data)
                        
        if current_run:
            results.append(current_run)
            
    except FileNotFoundError:
        print(f"Error: Could not find {filename}")
        return

    if not results:
        print("No data parsed. Check your output.txt format.")
        return

    
    run_labels = []
    runtimes = []
    comparisons = []
    memories = []
    colors = []
    
    color_map = {'Brute-Force': '#ff9999', 'Horspool': '#66b3ff', 'Boyer-Moore': '#99ff99'}

    for run in results:
        algo = run.get('Algorithm', 'Unknown')
        pattern = run.get('Pattern', '')
        occurrences = run.get('Occurrences', 0)
        
        run_labels.append(f"{algo}\n('{pattern}')\nMatches:\n {occurrences}")
        
        runtimes.append(run.get('Runtime', 0))
        comparisons.append(run.get('Comparisons', 0))
        memories.append(run.get('Memory', 0.0)) 
        colors.append(color_map.get(algo, 'gray'))

    
    fig, (ax1, ax2, ax3) = plt.subplots(1, 3, figsize=(15, 6))
    
    target_file = results[0].get('File', 'Unknown File')
    fig.suptitle(f"Performance Analysis for {target_file}", fontsize=18, fontweight='bold', y=1.05)

    
    bars1 = ax1.bar(run_labels, runtimes, color=colors)
    ax1.set_ylabel('Runtime (ms)', fontweight='bold')
    ax1.set_title('Algorithm Runtime', fontweight='bold')
    ax1.grid(axis='y', linestyle='--', alpha=0.7)
    for bar in bars1:
        yval = bar.get_height()
        ax1.text(bar.get_x() + bar.get_width()/2, yval, f"{int(yval)} ms", ha='center', va='bottom')

    
    bars2 = ax2.bar(run_labels, comparisons, color=colors)
    ax2.set_ylabel('Total Comparisons', fontweight='bold')
    ax2.set_title('Algorithmic Efficiency', fontweight='bold')
    ax2.grid(axis='y', linestyle='--', alpha=0.7)
    ax2.ticklabel_format(style='plain', axis='y')
    for bar in bars2:
        yval = bar.get_height()
        ax2.text(bar.get_x() + bar.get_width()/2, yval, f"{int(yval):,}", ha='center', va='bottom')

    
    bars3 = ax3.bar(run_labels, memories, color=colors)
    ax3.set_ylabel('Memory (MB)', fontweight='bold')
    ax3.set_title('Memory Footprint', fontweight='bold')
    ax3.grid(axis='y', linestyle='--', alpha=0.7)
    for bar in bars3:
        yval = bar.get_height()
        ax3.text(bar.get_x() + bar.get_width()/2, yval, f"{yval:.2f} MB", ha='center', va='bottom')

    
    plt.tight_layout()
    
    
    plt.savefig('algorithm_performance_results.png', dpi=300, bbox_inches='tight')
    print(f"Plot saved successfully for {target_file}")
    plt.show()

if __name__ == "__main__":
    parse_and_plot("output.txt")